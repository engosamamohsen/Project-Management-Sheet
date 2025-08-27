package com.example.projectmanagement.screens.bug.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.domain.bug.UploadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadImageViewModel @Inject constructor(
    private val uploadUseCase: UploadUseCase,
) : ViewModel() {
    private val _currentImageIndex = MutableStateFlow(0)
    val currentImageIndex = _currentImageIndex.asStateFlow()

    private val _totalImages = MutableStateFlow(0)
    val totalImages = _totalImages.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading = _isUploading.asStateFlow()

    private val _uploadSuccess = MutableStateFlow<List<String>?>(null)
    val uploadSuccess = _uploadSuccess.asStateFlow()

    fun uploadMultipleImages(uris: List<File>) {
        viewModelScope.launch {
            try {
                _isUploading.value = true
                _totalImages.value = uris.size
                _currentImageIndex.value = 0

                val result = uploadUseCase.uploadMultiple(uris) { current, total ->
                    _currentImageIndex.value = current
                    _totalImages.value = total
                    Log.d("Upload", "Progress: $current of $total images uploaded")
                }

                _uploadSuccess.value = result.data
                _isUploading.value = false
                Log.d("Upload", "All uploads completed: ${result.data}")

            } catch (e: Exception) {
                _isUploading.value = false
                _currentImageIndex.value = 0
                Log.e("Upload", "Upload failed: ${e.message}")
            }
        }
    }

    fun resetUploadState() {
        _currentImageIndex.value = 0
        _totalImages.value = 0
        _isUploading.value = false
        _uploadSuccess.value = null
    }
}