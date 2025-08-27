package com.example.projectmanagement.domain.bug

import com.example.projectmanagement.data.UploadImagesRepository
import com.example.projectmanagement.di.utils.BaseResponse
import com.example.projectmanagement.domain.base.BaseUseCase
import java.io.File
import javax.inject.Inject

class UploadUseCase @Inject constructor(
    private val repository: UploadImagesRepository,
) : BaseUseCase() {
    suspend fun uploadMultiple(
        request: List<File>,
        onProgress: (Int, Int) -> Unit
    ): BaseResponse<List<String>> {
        return repository.uploadMultiple(request, onProgress)
    }
}