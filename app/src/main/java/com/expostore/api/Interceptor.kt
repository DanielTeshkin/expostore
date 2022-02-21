package com.expostore.api

import android.content.Context
import com.expostore.data.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Fedotov Yakov
 */
class Interceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // TODO: переделать получение токена, когда будет бд!!!
        val token = AppPreferences.getSharedPreferences(context).getString("token", "")

        val request = chain.request()
            .newBuilder()

        token?.takeIf { it.isNotEmpty() }?.let { request.addHeader(TOKEN, it) }

        return chain.proceed(request.build())
    }
}

private const val TOKEN = "X-TOKEN-BEARER"
