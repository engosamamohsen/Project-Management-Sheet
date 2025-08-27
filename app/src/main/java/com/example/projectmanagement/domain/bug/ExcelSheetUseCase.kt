package com.example.projectmanagement.domain.bug

import com.example.projectmanagement.data.ExcelSheetRepository
import com.example.projectmanagement.di.utils.BaseResponse
import com.example.projectmanagement.domain.base.BaseUseCase
import com.example.projectmanagement.network.model.BugReport
import javax.inject.Inject

class ExcelSheetUseCase @Inject constructor(
    private val repository: ExcelSheetRepository,
) : BaseUseCase() {
    suspend fun submitBugReport(
        columns: Map<String, String>
    ): BaseResponse<Boolean> {
        return repository.submitBugReport(columns)
    }
    suspend fun createHeaders(
        headers: List<String>,
        sheetName: String = "Sheet1"
    ): BaseResponse<Boolean> {
        return repository.createHeaders(headers, sheetName)
    }


    suspend fun getAllBugReports(): BaseResponse<List<BugReport>> {
        return repository.getAllBugReports()
    }

    suspend fun checkConnection(): Boolean {
        return repository.testConnection()
    }
}