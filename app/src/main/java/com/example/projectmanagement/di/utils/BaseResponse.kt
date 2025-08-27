package com.example.projectmanagement.di.utils

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    val data: T,
    @SerializedName("message")
    val message: String
)