package com.expostore.api

import android.content.Context
import android.content.Intent
import android.util.Log
import com.expostore.api.base.ErrorResponse
import com.expostore.data.AppPreferences
import com.google.gson.Gson
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class Interceptor @Inject constructor(
    private val context: Context,
    private val apiWorker: Lazy<ApiWorker>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // TODO: переделать получение токена, когда будет бд!!!
        val token = AppPreferences.getSharedPreferences(context).getString("token", "")

        Log.wtf("TOKEN", token)

        val request = chain.request()
            .newBuilder()

        token?.takeIf { it.isNotEmpty() }?.let {
            request.addHeader(AUTHORIZATION, "$BEARER $it")
        }

        val response = chain.proceed(request.build())

        return processResponse(response, chain)
    }

    private fun processResponse(response: Response, chain: Interceptor.Chain): Response {
        if (response.headers(AUTHORIZATION).isNotEmpty() && response.code == 401) {
            triggerRebirth()
        }

        var currentResponse = response
        if (!currentResponse.isSuccessful) {
            when (fetchErrorCode(response.peekBody(Long.MAX_VALUE).string())) {
                TOKEN_OUTDATED -> tryReLogin(chain)?.let {
                    currentResponse = it
                }
            }
        }

        return currentResponse
    }

    private fun tryReLogin(chain: Interceptor.Chain): Response? {
        val newToken = runBlocking {
            apiWorker.get().refresh(
                AppPreferences.getSharedPreferences(context).getString(
                    "refresh",
                    ""
                ) ?: ""
            ).result
        }
        if (!newToken?.access.isNullOrEmpty()) {
            AppPreferences.getSharedPreferences(context).edit()
                .putString("token", newToken!!.access)
                .putString("refresh", newToken.refresh).apply()

            val request = chain.request()
                .newBuilder()
                .header(AUTHORIZATION, "$BEARER ${newToken.access}")
                .build()
            return chain.proceed(request)
        }
        return null
    }

    private fun fetchErrorCode(data: String): String =
        kotlin.runCatching {
            Gson().fromJson(
                data,
                ErrorResponse::class.java
            ).code
        }.getOrDefault("")

    private fun triggerRebirth() {
        AppPreferences.getSharedPreferences(context).edit().putString("token", "").apply()
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}

private const val AUTHORIZATION = "Authorization"
private const val BEARER = "Bearer"

private const val TOKEN_OUTDATED = "token_not_valid"