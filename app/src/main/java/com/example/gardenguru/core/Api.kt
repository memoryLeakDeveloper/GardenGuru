package com.example.gardenguru.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {

    private val baseUrl = "http://192.168.31.122:8000"

    fun <T> makeService(clazz: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(clazz)

    private fun okHttpClient() =
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

    companion object {
        const val apiVersion = "/api/v1"
    }
}