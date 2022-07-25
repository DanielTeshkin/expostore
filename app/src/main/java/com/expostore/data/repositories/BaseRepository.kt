package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.base.ApiErrorResponse
import com.expostore.api.base.ApiResponse
import com.expostore.api.base.BaseApiResponse
import com.expostore.db.LocalWorker
import com.expostore.utils.decodeImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

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

    protected suspend fun <T> singleOperator(databaseQuery: suspend ()-> T,
                                 networkCall: suspend () ->  T,
                                 saveCallResult:   (T) -> Unit)=flow {
        val result = databaseQuery.invoke()
        emit(result)
        val response=networkCall.invoke()
        saveCallResult(response)
        emit(response)
    }.first{it!=null}





}