package com.expostore.data.local.db

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val localWorker: LocalWorker) {

    fun getToken()= flow{
        val result= localWorker.getToken()
        emit(result)
    }
}