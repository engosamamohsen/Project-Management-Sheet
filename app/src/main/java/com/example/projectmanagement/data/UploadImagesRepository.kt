package com.example.projectmanagement.data

import android.util.Log
import com.example.projectmanagement.BuildConfig
import com.example.projectmanagement.network.api.ApiService
import com.example.projectmanagement.di.utils.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UploadImagesRepository @Inject constructor(private val apiService: ApiService) {
    private val IMGBB_API_KEY = BuildConfig.IMGBB_API_KEY

    //29fe05a1a12c3f25ae0246d98c3372f8
    val TAG = "BugRepository"
    suspend fun uploadMultiple(
        files: List<File>,
        onProgress: (Int, Int) -> Unit
    ): BaseResponse<List<String>> = withContext(Dispatchers.IO) {
        val downloadUrls = mutableListOf<String>()
        Log.d(TAG, "Starting upload of ${files.size} images to ImgBB")

        try {
            files.forEachIndexed { index, file ->
                Log.d(TAG, "Uploading image ${index + 1} of ${files.size}")

                // Convert URI to File
                try {
                    // Upload to ImgBB
                    val imageUrl = uploadToImgBB(file)
                    downloadUrls.add(imageUrl)

                    // Update progress
                    onProgress(index + 1, files.size)
                    Log.d(TAG, "Image ${index + 1} uploaded successfully: $imageUrl")

                } catch (uploadException: Exception) {
                    Log.e(TAG, "Failed to upload image ${index + 1}: ${uploadException.message}")
                    throw uploadException
                } finally {
                    // Clean up temp file
                    if (file.exists()) {
                        file.delete()
                    }
                }
            }

            return@withContext BaseResponse(
                data = downloadUrls,
                message = "All images uploaded successfully"
            )

        } catch (exception: Exception) {
            Log.e(TAG, "Upload failed: ${exception.message}")
            throw exception
        }
    }

    private suspend fun uploadToImgBB(file: File): String = withContext(Dispatchers.IO) {
        Log.d(TAG, "Uploading file: ${file.name}, size: ${file.length()} bytes")

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        try {
            // Use the injected ApiService - returns ImgBBApiResponse
            val response = apiService.uploadToImgBB(IMGBB_API_KEY, imagePart)

            Log.d(TAG, "ImgBB Response Success: ${response.success}")
            Log.d(TAG, "ImgBB Response Status: ${response.status}")

            if (response.success) {
                val imageUrl = response.data.url
                Log.d(TAG, "Successfully got image URL: $imageUrl")
                return@withContext imageUrl
            } else {
                throw Exception("ImgBB upload failed: success = false")
            }

        } catch (exception: Exception) {
            Log.e(TAG, "Failed to upload to ImgBB: ${exception.message}")
            throw exception
        }
    }

}