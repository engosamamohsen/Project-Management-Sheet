package com.example.projectmanagement.network.repository

import com.example.projectmanagement.network.di.GoogleSheetsService
import com.example.projectmanagement.network.model.BugReport
import javax.inject.Inject
import javax.inject.Singleton


interface NetworkRepository {
    suspend fun testConnection(): Boolean
    suspend fun addBugReport(columns: Map<String, String>): Boolean
    suspend fun getAllBugReports(): List<BugReport>
    suspend fun ensureHeaders(headers: List<String>): Boolean
}


@Singleton
class GoogleSheetsRepository @Inject constructor(
    private val googleSheetsService: GoogleSheetsService
) : NetworkRepository {

    override suspend fun testConnection(): Boolean {
        return googleSheetsService.testConnection()
    }

    override suspend fun addBugReport(columns: Map<String, String>): Boolean {
        return googleSheetsService.addBugReport(columns)
    }

    override suspend fun getAllBugReports(): List<BugReport> {
        return googleSheetsService.getAllBugReports()
    }

    override suspend fun ensureHeaders(headers: List<String>): Boolean {
        return googleSheetsService.ensureHeaders(headers)
    }
}