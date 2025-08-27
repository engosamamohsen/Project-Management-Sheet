package com.example.projectmanagement.di

import android.content.Context
import com.example.projectmanagement.network.api.ApiService
import com.example.projectmanagement.network.config.NetworkConfig
import com.example.projectmanagement.network.di.GoogleSheetsService
import com.example.projectmanagement.network.di.NetworkConfigImpl
import com.example.projectmanagement.network.repository.GoogleSheetsRepository
import com.example.projectmanagement.network.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfig {
        return NetworkConfigImpl()
    }

    @Provides
    @Singleton
    fun provideGoogleSheetsService(
        @ApplicationContext context: Context,
        networkConfig: NetworkConfig
    ): GoogleSheetsService {
        return GoogleSheetsService(context, networkConfig)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(
        googleSheetsService: GoogleSheetsService
    ): NetworkRepository {
        return GoogleSheetsRepository(googleSheetsService)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.imgbb.com/") // Your API base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}