package com.example.projectmanagement.data

import android.util.Log
import com.example.projectmanagement.BuildConfig
import com.example.projectmanagement.di.GoogleSheetsService
import com.example.projectmanagement.di.utils.BaseResponse
import com.example.projectmanagement.model.BugReport
import com.example.projectmanagement.screens.bug.viewModel.EXCEL_COLUMN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExcelSheetRepository @Inject constructor(private val googleSheetsService: GoogleSheetsService) {

    val TAG = "ExcelSheetRepository"
    private val EXCEL_API_KEY = BuildConfig.EXCEL_API_KEY

    suspend fun submitBugReport(
        columns: Map<String, String>
    ): BaseResponse<Boolean> = withContext(Dispatchers.IO) {
        try {
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(Date())
            val mutableColumns = columns.toMutableMap()
            mutableColumns[EXCEL_COLUMN.TIMESTAMP.name] = timestamp
            mutableColumns[EXCEL_COLUMN.STATUS.name] = "open"

            val success = googleSheetsService.addBugReport(
                columns = mutableColumns
            )

            if (success) {
                return@withContext BaseResponse(
                    data = true,
                    message = "Bug report submitted successfully to Google Sheets"
                )
            } else {
                throw Exception("Failed to submit bug report to Google Sheets")
            }

        } catch (exception: Exception) {
            Log.e(TAG, "Failed to submit bug report: ${exception.message}")
            throw exception
        }
    }

    //for test connection in debug mode
    suspend fun testConnection() : Boolean{
        return googleSheetsService.testConnection()
    }

    // Function to get all bug reports
    suspend fun getAllBugReports(): BaseResponse<List<BugReport>> = withContext(Dispatchers.IO) {
        try {
            val bugReports = googleSheetsService.getAllBugReports()
            return@withContext BaseResponse(
                data = bugReports,
                message = "Bug reports retrieved successfully"
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to retrieve bug reports: ${exception.message}")
            throw exception
        }
    }


    // Add method to manually create headers
    suspend fun createHeaders(
        headers: List<String>,
        sheetName: String = "Sheet1"
    ): BaseResponse<Boolean> = withContext(Dispatchers.IO) {
        try {
            val success = googleSheetsService.ensureHeaders(headers, sheetName = sheetName)

            if (success) {
                return@withContext BaseResponse(
                    data = true,
                    message = "Headers created successfully"
                )
            } else {
                throw Exception("Failed to create headers")
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to create headers: ${exception.message}")
            throw exception
        }
    }


}