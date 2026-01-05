package com.codigosdeprogramacion.mistock.data.local

import android.content.Context

object PreferencesManager {

    private const val PREFS = "settings"
    private const val BASE_URL = "base_url"

    fun saveBaseUrl(context: Context, url: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(BASE_URL, url)
            .apply()
    }

    fun getBaseUrl(context: Context): String {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(BASE_URL, "http://192.168.0.1/mi-stock/")!!
    }
}
