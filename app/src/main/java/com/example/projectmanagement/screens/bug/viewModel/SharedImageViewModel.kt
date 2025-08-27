package com.example.projectmanagement.screens.bug.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SharedImageViewModel @Inject constructor() : ViewModel() {
    private val _sharedImages = MutableStateFlow<List<Uri>>(emptyList())
    val sharedImages: StateFlow<List<Uri>> = _sharedImages.asStateFlow()

    fun updateSharedImages(images: List<Uri>) {
        _sharedImages.value = images
    }

    fun clearSharedImages() {
        _sharedImages.value = emptyList()
    }
}