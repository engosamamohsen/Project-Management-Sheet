package com.example.projectmanagement.di

import com.example.projectmanagement.di.utils.ImgBBApiResponse
import okhttp3.MultipartBody

import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("upload")
    suspend fun uploadToImgBB(
        @Query("key") apiKey: String,
        @Part image: MultipartBody.Part
    ): ImgBBApiResponse
}