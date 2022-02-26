package com.expostore.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// TODO: выпилить из проекта
object Retrofit {
    lateinit var apiWorker: ApiWorkerImpl
        private set
    const val BASE_URL = "https://expostore.ru"

    fun build(context: Context) {
        if (::apiWorker.isInitialized) {
            return
        }
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Interceptor(context))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(ServerApi::class.java)
        apiWorker = ApiWorkerImpl(api)
    }
}