package com.example.tak_81_c_chatbot_app

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChatBotAPIClient {
    private const val BASE_URL = "http://10.0.2.2:5000/"
    private val retrofit: Retrofit by lazy {
        // Create OkHttpClient with logging interceptor
        val httpClient = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    fun getClientWithLogging(): Retrofit = retrofit
}
