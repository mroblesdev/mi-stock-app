package com.codigosdeprogramacion.mistock.data.remote

import com.codigosdeprogramacion.mistock.data.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("index.php")
    fun getProduct(@Query("code") code: String): Call<Product>
}
