package com.example.projectmanagement.network.di

import android.content.Context
import android.util.Log
import com.example.projectmanagement.network.config.NetworkConfig
import com.example.projectmanagement.network.model.BugReport
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleSheetsService @Inject constructor(
    private val context: Context,
    private val config: NetworkConfig
) {
    companion object {
        private const val TAG = "GoogleSheetsService"
        private const val APPLICATION_NAME = "Project Management Bug Reports"
        private val SCOPES = listOf(SheetsScopes.SPREADSHEETS)
        private val JSON_FACTORY = GsonFactory.getDefaultInstance()
    }
    private val SPREADSHEET_ID = config.spreadsheetId
    private val RANGE = "Sheet1!A:D"

    //for debug mode check test connection
    suspend fun testConnection(): Boolean = withContext(Dispatchers.IO) {
        try {
            val service = getSheetsService()
            val response = service.spreadsheets().get(SPREADSHEET_ID).execute()
            Log.d(TAG, "Sheet title: ${response.properties.title}")
            return@withContext true
        } catch (e: Exception) {
            Log.e(TAG, "Test connection failed: ${e.message}")
            return@withContext false
        }
    }


    private suspend fun getSheetsService(): Sheets = withContext(Dispatchers.IO) {
        try {
            // Validate that we have required credentials
            if (config.serviceAccountClientEmail.isBlank() ||
                config.serviceAccountPrivateKey.isBlank()
            ) {
                throw IllegalStateException("Service account credentials not found.")
            }

            val httpTransport = GoogleNetHttpTransport.newTrustedTransport()

            // Create service account JSON from config
            val serviceAccountJson = """
                {
                  "type": "${config.serviceAccountType}",
                  "project_id": "${config.serviceAccountProjectId}",
                  "private_key_id": "${config.serviceAccountPrivateKeyId}",
                  "private_key": "${config.serviceAccountPrivateKey}",
                  "client_email": "${config.serviceAccountClientEmail}",
                  "client_id": "${config.serviceAccountClientId}",
                  "auth_uri": "${config.serviceAccountAuthUri}",
                  "token_uri": "${config.serviceAccountTokenUri}",
                  "auth_provider_x509_cert_url": "${config.serviceAccountAuthProviderCertUrl}",
                  "client_x509_cert_url": "${config.serviceAccountClientCertUrl}",
                  "universe_domain": "googleapis.com"
                }
            """.trimIndent()

            val inputStream = ByteArrayInputStream(serviceAccountJson.toByteArray())

            val credential = GoogleCredential.fromStream(inputStream)
                .createScoped(SCOPES)

            Log.d(TAG, "Google Sheets service created successfully")
            Log.d(TAG, "Using service account: ${config.serviceAccountClientEmail}")

            return@withContext Sheets.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build()

        } catch (e: Exception) {
            Log.e(TAG, "Failed to create Sheets service: ${e.message}", e)
            throw e
        }
    }

    suspend fun ensureHeaders(
        headers: List<String>,
        spreadsheetId: String = SPREADSHEET_ID,
        sheetName: String = "Sheet1"
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val service = getSheetsService()

            // Check if headers already exist
            val headerRange = "${sheetName}!A1:Z1"
            val headerResponse = service.spreadsheets().values()
                .get(spreadsheetId, headerRange)
                .execute()

            val existingHeaders =
                headerResponse.getValues()?.firstOrNull()?.map { it.toString() } ?: emptyList()

            if (existingHeaders.isEmpty()) {
                // No headers exist, create them
                val values = listOf(headers)
                val body = ValueRange().setValues(values)
                val range = "${sheetName}!A1:${getColumnLetter(headers.size)}1"

                service.spreadsheets().values()
                    .update(spreadsheetId, range, body)
                    .setValueInputOption("RAW")
                    .execute()

                Log.d(TAG, "Headers created: ${headers.joinToString(", ")}")
                return@withContext true
            } else {
                // Headers exist, check if we need to add new ones
                val newHeaders = headers.filter { !existingHeaders.contains(it) }
                if (newHeaders.isNotEmpty()) {
                    val allHeaders = existingHeaders + newHeaders
                    val values = listOf(allHeaders)
                    val body = ValueRange().setValues(values)
                    val range = "${sheetName}!A1:${getColumnLetter(allHeaders.size)}1"

                    service.spreadsheets().values()
                        .update(spreadsheetId, range, body)
                        .setValueInputOption("RAW")
                        .execute()

                    Log.d(TAG, "New headers added: ${newHeaders.joinToString(", ")}")
                }
                return@withContext true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to ensure headers: ${e.message}")
            return@withContext false
        }
    }


    suspend fun addBugReport(
        columns: Map<String, String>,
        spreadsheetId: String = SPREADSHEET_ID,
        sheetName: String = "Sheet1",
        startColumn: String = "A"
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val service = getSheetsService()

            // Ensure headers exist before adding data
            val headerKeys = columns.keys.toList()
            if (!ensureHeaders(headerKeys, spreadsheetId, sheetName)) {
                throw Exception("Failed to ensure headers in sheet")
            }

            // Get current headers (after ensuring they exist)
            val headerRange = "${sheetName}!${startColumn}1:Z1"
            val headerResponse = service.spreadsheets().values()
                .get(spreadsheetId, headerRange)
                .execute()

            val headers =
                headerResponse.getValues()?.firstOrNull()?.map { it.toString() } ?: emptyList()

            // Map columns to their positions based on headers
            val rowData = mutableListOf<Any>()
            headers.forEach { header ->
                val value = columns[header] ?: ""
                rowData.add(value)
            }

            val values = listOf(rowData)
            val body = ValueRange().setValues(values)
            val range = "${sheetName}!${startColumn}:${getColumnLetter(headers.size)}"

            val result = service.spreadsheets().values()
                .append(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .execute()

            Log.d(
                TAG,
                "Bug report added successfully to $sheetName. Updated ${result.updates?.updatedRows} rows"
            )
            return@withContext true

        } catch (e: Exception) {
            Log.e(TAG, "Failed to add bug report to Google Sheets: ${e.message}")
            return@withContext false
        }
    }


    // Helper function to convert column index to letter (A, B, C, etc.)
    private fun getColumnLetter(columnIndex: Int): String {
        var index = columnIndex - 1
        var result = ""
        while (index >= 0) {
            result = ('A' + (index % 26)) + result
            index = index / 26 - 1
        }
        return result
    }

    suspend fun getAllBugReports(): List<BugReport> = withContext(Dispatchers.IO) {
        try {
            val service = getSheetsService()

            val response = service.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute()

            val values = response.getValues()
            val bugReports = mutableListOf<BugReport>()

            values?.drop(1)?.forEach { row ->
                if (row.size >= 4) {
                    bugReports.add(
                        BugReport(
                            TIMESTAMP = row[0].toString(),
                            DESCRIPTION = row[1].toString(),
                            IMAGE_URLS = row[2].toString().split(", ").filter { it.isNotBlank() },
                            STATUS = row[3].toString()
                        )
                    )
                }
            }

            Log.d(TAG, "Retrieved ${bugReports.size} bug reports")
            return@withContext bugReports

        } catch (e: Exception) {
            Log.e(TAG, "Failed to retrieve bug reports: ${e.message}")
            return@withContext emptyList()
        }
    }
}