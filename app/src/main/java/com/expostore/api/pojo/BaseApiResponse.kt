package com.expostore.api.pojo

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Response


/**
 * @author Fedotov Yakov
 */
interface BaseApiResponse<T> {
    var result: T?

    companion object {
        internal val gson = GsonBuilder().create()
    }
}

data class ApiResponse<T>(
    override var result: T? = null,
    val message: String = ""
) : BaseApiResponse<T> {
    companion object {
        fun <T> create(response: Response<T>): BaseApiResponse<T> {
            return if (response.isSuccessful) {
                ApiResponse(response.body())
            } else {
                val type = object : TypeToken<ApiErrorResponse<T>>() {}.type
                BaseApiResponse.gson.fromJson<ApiErrorResponse<T>>(
                    response.errorBody()?.string() ?: "", type
                )
            }
        }

        fun <T> createList(response: Response<List<T>>): BaseApiResponse<List<T>> {
            return if (response.isSuccessful) {
                ApiResponse(response.body())
            } else {
                val type = object : TypeToken<ApiErrorResponse<List<T>>>() {}.type
                BaseApiResponse.gson.fromJson<ApiErrorResponse<List<T>>>(
                    response.errorBody()?.string() ?: "", type
                )
            }
        }
    }
}

data class ApiListResponse<T>(
    override var result: List<T>? = null
) : BaseApiResponse<List<T>> {
    companion object {
        fun <T : BaseApiResponse<T>> createList(response: Response<List<T>>): BaseApiResponse<List<T>> {
            return if (response.isSuccessful) {
                ApiListResponse(response.body())
            } else {
                val type = object : TypeToken<ApiErrorResponse<List<T>>>() {}.type
                BaseApiResponse.gson.fromJson<ApiErrorResponse<List<T>>>(
                    response.errorBody()?.string() ?: "", type
                )
            }
        }
    }
}

data class ApiErrorResponse<T>(
    @JsonProperty("message")
    val message: String = "",
    override var result: T? = null
) : BaseApiResponse<T>
