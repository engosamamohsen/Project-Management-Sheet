package com.example.projectmanagement.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.projectmanagement.common.language.AppPreferences
import com.example.projectmanagement.common.language.MySharedPreferences
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(){
    companion object {
        lateinit  var appContext: Context
    }

    @Inject
    lateinit var appPreferences: MySharedPreferences
    val TAG = "MyApplication"

    override fun onCreate() {
        appContext = applicationContext
        super.onCreate()
        // Initialize Firebase first
        Log.d(TAG, "onCreate: start")
        appPreferences.setLanguage(appPreferences.getCurrentLanguage())
        setDefaultLanguage(appPreferences.getCurrentLanguage(this))
    }

    private fun setDefaultLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}