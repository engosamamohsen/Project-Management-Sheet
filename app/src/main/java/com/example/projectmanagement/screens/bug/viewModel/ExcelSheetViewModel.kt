package com.example.projectmanagement.screens.bug.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.domain.bug.ExcelSheetUseCase
import com.example.projectmanagement.network.model.BugReport
import com.example.projectmanagement.screens.bug.EXCEL_COLUMN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2

@HiltViewModel
class ExcelSheetViewModel @Inject constructor(
    private val excelSheetUseCase: ExcelSheetUseCase
) : ViewModel() {

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _submitSuccess = MutableStateFlow<Boolean?>(null)
    val submitSuccess: StateFlow<Boolean?> = _submitSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    private val _bugReports = MutableStateFlow<List<BugReport>>(emptyList())
    val bugReports: StateFlow<List<BugReport>> = _bugReports

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    val TAG ="ExcelSheetViewModel"

    //for add custom header
    fun createCustomHeaders() {
        viewModelScope.launch {
            try {
                val enumHeaders = EXCEL_COLUMN.values().map { it.name }
                val response = excelSheetUseCase.createHeaders(enumHeaders)
                // Handle response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun submitBugReport(vararg fields: Map<String, Any>) {
        viewModelScope.launch {
            try {
                createCustomHeaders()
                _isSubmitting.value = true
                _errorMessage.value = null

                val columns = hashMapOf<String, String>().apply {
                    fields.forEach { map ->
                        map.forEach { (key, value) ->
                            put(
                                key,
                                when (value) {
                                    is List<*> -> value.joinToString(",")
                                    else -> value.toString()
                                }
                            )
                        }
                    }
                }

                val response = excelSheetUseCase.submitBugReport(columns)
                _submitSuccess.value = response.data

            } catch (e: Exception) {
                _submitSuccess.value = false
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isSubmitting.value = false
            }
        }
    }



    // Load all bug reports from Excel sheet
    fun loadBugReports() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                Log.d(TAG, "Loading bug reports from Excel sheet")
                val response = excelSheetUseCase.getAllBugReports()

                if (response.data != null) {
                    _bugReports.value = response.data
                    Log.d(TAG, "Loaded ${response.data.size} bug reports")
                } else {
                    _errorMessage.value = "Failed to load bug reports"
                    Log.e(TAG, "Failed to load bug reports: ${response.message}")
                }

            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred"
                Log.e(TAG, "Error loading bug reports: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun testConnection(){
        viewModelScope.launch {
            try {
                excelSheetUseCase.checkConnection()
            }catch (e: Exception){
                _errorMessage.value = e.message ?: "Unknown error occurred"
                Log.e(TAG, "Error loading bug reports: ${e.message}")
            }

        }
    }

    fun resetState() {
        _submitSuccess.value = null
        _errorMessage.value = null
    }
}