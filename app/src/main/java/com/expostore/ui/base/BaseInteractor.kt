package com.expostore.ui.base

import com.expostore.api.pojo.ApiErrorResponse
import com.expostore.api.pojo.ApiResponse
import com.expostore.api.pojo.BaseApiResponse
import java.lang.Exception

/**
 * @author Fedotov Yakov
 */
open class BaseInteractor {
    protected suspend inline fun <T : BaseApiResponse<T>> handle(crossinline action: suspend () -> BaseApiResponse<T>): T? {
        return when(val result = action.invoke()){
            is ApiResponse<T> -> result.result
            else -> throw Exception()
        }
    }

    protected suspend inline fun <T> handleList(crossinline action: suspend () -> BaseApiResponse<List<T>>): List<T>? {
        return when(val result = action.invoke()){
            is ApiResponse<List<T>> -> result.result
            else -> throw Exception()
        }
    }
}