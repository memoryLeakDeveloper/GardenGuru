package com.entexy.gardenguru.core

import com.entexy.gardenguru.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SupportApi {

    fun <T> makeService(clazz: Class<T>): T =
        Retrofit.Builder().baseUrl(BuildConfig.FEEDBACK_URL_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(makeOkHttpClient())
            .build()
            .create(clazz)

    private fun makeOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
        return builder.build()
    }

    companion object {
        const val apiVersion = "/api/v1"
    }
}