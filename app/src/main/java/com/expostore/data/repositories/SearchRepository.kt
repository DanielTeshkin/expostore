package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.response.SaveSearchRequest
import com.expostore.api.response.SaveSearchResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiWorker: ApiWorker) :BaseRepository(){

    fun saveSearch(saveSearchRequest: SaveSearchRequest) = flow{
        val result=handleOrDefault(SaveSearchResponse()){apiWorker.saveSearch(saveSearchRequest)}
        emit(result)
    }
}