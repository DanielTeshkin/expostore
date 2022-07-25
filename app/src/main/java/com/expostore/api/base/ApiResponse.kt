package com.expostore.api.base

import retrofit2.Response


data class ApiResponse<T>(
    override var result: T? = null,
    val message: String = ""
) : BaseApiResponse<T>() {
    companion object {
        fun <T> create(response: Response<T>): BaseApiResponse<T> {
            return if (response.isSuccessful) {
                ApiResponse(response.body())
            } else {
                val errorResult =
                    gson.fromJson(response.errorBody()?.string() ?: "", ErrorResponse::class.java)
                ApiErrorResponse(errorResult.detail)
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