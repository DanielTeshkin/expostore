package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.response.SaveSearchRequest
import com.expostore.data.remote.api.response.SaveSearchResponse
import com.expostore.data.local.db.LocalWorker
import com.expostore.model.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker):BaseRepository(){

    fun saveSearch(saveSearchRequest: SaveSearchRequest) = flow{
        val result=handleOrDefault(SaveSearchResponse()){apiWorker.saveSearch(saveSearchRequest)}
        emit(result)
    }
    fun saveSearchList()= flow {
        val result=handleOrEmptyList { apiWorker.getSaveSearchList() }.map { it.toModel }
        emit(result)
    }

    fun deleteSaveSearch(id:String) = flow {
        val result=handleOrDefault(SaveSearchResponse()){apiWorker.deleteSaveSearch(id)}
        emit(result)
    }
}