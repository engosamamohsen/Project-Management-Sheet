package com.example.projectmanagement.common.language

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.projectmanagement.app.MyApplication
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPreferences @Inject constructor(val context: Context) {
    val PREFERENCES_NAME = "app-sharedPreferences"
    val LANGUAGE = "language"
    val DEFAULT_LANGUAGE = "en"

    private fun getEncryptedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setSystemLocale(config: Configuration, locale: Locale?) {
        config.setLocale(locale)
    }

    fun changeLanguage(languageToLoad: String) {
        val locale = Locale.forLanguageTag(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        setSystemLocale(config, locale)
        context.createConfigurationContext(config)
        MyApplication.appContext.createConfigurationContext(config)
        setLanguage(languageToLoad)
    }

    fun setKey(key: String, value: String) {
        getEncryptedPreferences().edit { putString(key, value) }
    }

    fun getKey(key: String): String {
        return getEncryptedPreferences().getString(key, "") ?: ""
    }

    fun setLanguage(language: String) {
        getEncryptedPreferences().edit { putString(LANGUAGE, language) }
    }

    fun getCurrentLanguage(currentContext: Context? = context): String {
        val preferences = getEncryptedPreferences()
        val language = preferences.getString(LANGUAGE, "")

        return if (!language.isNullOrEmpty()) {
            language
        } else {
            setLanguage(DEFAULT_LANGUAGE)
            DEFAULT_LANGUAGE
        }
    }
}