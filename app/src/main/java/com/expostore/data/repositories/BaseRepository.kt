package com.expostore.data.repositories

import com.expostore.api.base.ApiResponse
import com.expostore.data.remote.api.base.ApiErrorResponse

import com.expostore.data.remote.api.base.BaseApiResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * @author Teshkin Daniel
 */
open class BaseRepository {


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
    protected fun <T> operator (databaseQuery: suspend ()-> T,
                                networkCall: suspend () ->  T,
                                saveCallResult: suspend  (T) -> Unit) = flow {
        val result = databaseQuery.invoke()
        emit(result)
        val response=networkCall.invoke()
        saveCallResult(response)
        emit(response)
    }

    protected fun <T,A> singleOperator(databaseQuery: suspend ()-> A,
                                       mapper:(A)->T,
                                 networkCall: suspend () ->  T,
                                 saveCallResult: suspend  (T) -> Unit)=flow {
        val result =databaseQuery.invoke()
        if(result!=null) emit(mapper(result))
        val response=networkCall.invoke()
        saveCallResult(response)
        emit(response)
    }




}