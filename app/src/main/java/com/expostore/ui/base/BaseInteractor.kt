package com.expostore.ui.base

import com.expostore.api.base.ApiErrorResponse
import com.expostore.api.base.ApiResponse
import com.expostore.api.base.BaseApiResponse


/**
 * @author Fedotov Yakov
 */
open class BaseInteractor {

    protected suspend inline fun <T> handleOrDefault(
        default: T,
        crossinline action: suspend () -> BaseApiResponse<T>
    ): T = handle(action) ?: default

    protected suspend inline fun <T> handle(crossinline action: suspend () -> BaseApiResponse<T>): T? {
        return when (val result = action.invoke()) {
            is ApiResponse -> result.result
            is ApiErrorResponse -> throw result.exception
            else -> throw IllegalStateException()
        }
    }

    protected suspend inline fun <T> handleOrEmptyList(crossinline action: suspend () -> BaseApiResponse<List<T>>): List<T> {
        return handleList(action) ?: emptyList()
    }

    protected suspend inline fun <T> handleList(crossinline action: suspend () -> BaseApiResponse<List<T>>): List<T>? {
        return when (val result = action.invoke()) {
            is ApiResponse<List<T>> -> result.result
            is ApiErrorResponse -> throw result.exception
            else -> throw IllegalStateException()
        }
    }
}