package com.example.projectmanagement.domain.base

import android.util.Log
import android.widget.Toast
import com.example.projectmanagement.di.utils.Resource
import com.example.projectmanagement.app.MyApplication
import com.example.projectmanagement.di.utils.ImgBBApiResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase {
    protected fun <T> execute(
        coroutineContext: CoroutineContext,
        apiCall: suspend () -> T
    ): Flow<Resource<T>> = flow {
        val TAG = "BASE_Use_Case"
        emit(Resource.Loading)
        try {
            val response = apiCall()
            val imgBBResponse = (response as ImgBBApiResponse)
            Log.d(TAG, "execute: ${imgBBResponse}")
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            Log.d(TAG, "execute: HttpException ${e.code()}")

            try {
                val errorBody = e.response()?.errorBody()?.string()

                // First try to parse as JSON
                if (!errorBody.isNullOrEmpty()) {
                    try {
                        val gson = GsonBuilder().setLenient().create()
                        val type = object : TypeToken<ImgBBApiResponse>() {}.type
                        val imgBBResponse: ImgBBApiResponse = gson.fromJson(errorBody, type)
                        emit(Resource.Failure(e.code(), imgBBResponse.status.toString()))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(MyApplication.appContext, imgBBResponse.status, Toast.LENGTH_LONG).show()
                        }
                    } catch (jsonParseException: Exception) {
                        // If JSON parsing fails, show the raw error message
                        emit(Resource.Failure(e.code(), errorBody))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(MyApplication.appContext, errorBody, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    emit(Resource.Failure(e.code(), e.message()))
                }
            } catch (innerE: Exception) {
                emit(Resource.Failure(e.code(), e.message()))
            }
        } catch (e: Exception) {
            Log.d(TAG, "execute Exception ${e.message}")
            emit(Resource.Failure(message = e.localizedMessage))
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApplication.appContext, e.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        }
    }.flowOn(coroutineContext)
}