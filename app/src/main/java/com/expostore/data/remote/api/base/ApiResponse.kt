package com.expostore.api.base

import com.expostore.data.remote.api.base.ApiErrorResponse
import com.expostore.data.remote.api.base.BaseApiResponse
import com.expostore.data.remote.api.base.ErrorResponse
import retrofit2.Response


data class ApiResponse<T>(
    override var result: T? = null,
    val message: String = ""
) : BaseApiResponse<T>() {
    companion object {
        fun <T> create(response: Response<T>): BaseApiResponse<T> {
            return when (response.isSuccessful) {
                true -> ApiResponse(response.body())
                false -> {
                    val errorResult =
                        gson.fromJson(
                            response.errorBody()?.string() ?: "",
                            ErrorResponse::class.java
                        )
                    ApiErrorResponse(errorResult.detail)
                }
            }
        }

        fun <T> createList(response: Response<List<T>>): BaseApiResponse<List<T>> {
            return if (response.isSuccessful) {
                ApiResponse(response.body())
            } else {
                val errorResult =
                    gson.fromJson(response.errorBody()?.string() ?: "", ErrorResponse::class.java)
                ApiErrorResponse(errorResult.detail)
            }
        }
    }
}