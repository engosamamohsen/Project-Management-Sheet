package com.example.projectmanagement.di.utils

sealed class Resource<out T> {
    class Success<out T>(val value: T) : Resource<T>()
    class Failure(val status: Int? = null, val message: String? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>() // Pass isLoading for progress
    object Default : Resource<Nothing>()
}