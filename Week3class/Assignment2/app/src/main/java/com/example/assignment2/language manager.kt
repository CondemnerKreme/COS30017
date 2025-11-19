package com.example.assignment2

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale

object LanguageManager {

    private const val SELECTED_LANGUAGE = "selected_language"

    fun setAppLanguage(context: Context, languageCode: String) {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(SELECTED_LANGUAGE, languageCode).apply()
    }

    fun getSavedLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        return sharedPreferences.getString(SELECTED_LANGUAGE, "en") ?: "en"
    }

    fun updateAppLanguage(context: Context, languageCode: String) {
        setAppLanguage(context, languageCode)
        setLocale(context, languageCode)
    }

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun applySavedLanguage(context: Context) {
        val savedLanguage = getSavedLanguage(context)
        setLocale(context, savedLanguage)
    }
}