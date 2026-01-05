package com.codigosdeprogramacion.mistock.data.remote

import android.content.Context
import com.codigosdeprogramacion.mistock.data.local.PreferencesManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getService(context: Context): ApiService {
        val baseUrl = PreferencesManager.getBaseUrl(context)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
