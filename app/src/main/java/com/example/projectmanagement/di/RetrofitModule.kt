package com.example.projectmanagement.di

import android.content.Context
import com.example.projectmanagement.preferences.AppPreferences
import com.example.projectmanagement.app.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val REQUEST_TIME_OUT: Long = 60

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
//    val logging = HttpLoggingInterceptor(ApiLogger())
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        logging: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
            .addNetworkInterceptor(logging)
//        .addInterceptor(ChuckInterceptor(context))
            .build()
    }



    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.imgbb.com/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client) // Use custom OkHttpClient
        .build()

    @Provides
    @Singleton
    fun provideContext(
    ): Context = MyApplication.appContext


    @Provides
    @Singleton
    fun provideHeadersInterceptor(appPreferences: AppPreferences) = run {
        Interceptor { chain ->
            val request = chain.request().newBuilder()
            request.removeHeader("Accept")
            request.removeHeader("Content-Type")
            request.removeHeader("Authorization")

            request.addHeader("Accept", "application/json")
            request.addHeader("Content-Type", "application/json")
            if (appPreferences.getIsLoggedIn())
                request.addHeader("Authorization", "Bearer ${appPreferences.getUser().token.toString()}")
            chain.proceed(
                request.build()
            )
        }
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    // Add this provider for GoogleSheetsService
    @Provides
    @Singleton
    fun provideGoogleSheetsService(context: Context): GoogleSheetsService {
        return GoogleSheetsService(context)
    }

}