package com.expostore.db

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val localWorker: LocalWorker) {

    fun getToken()= flow{
        val result= localWorker.getToken()
        emit(result)
    }
}