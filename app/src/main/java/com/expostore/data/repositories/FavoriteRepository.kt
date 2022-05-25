package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.NoteRequest

import com.expostore.api.response.NoteResponse
import com.expostore.api.response.SaveSearchResponse
import com.expostore.api.response.SelectionResponse

import com.expostore.model.category.toModel
import com.expostore.model.favorite.toModel
import com.expostore.model.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val apiWorker: ApiWorker): BaseRepository() {
    fun getFavoritesList() = flow{
        val result=handleOrEmptyList { apiWorker.getFavoritesList() }.map { it.toModel }
        emit(result)
    }
    fun updateSelected(id:String)=flow{
        val result=handleOrDefault(SelectFavoriteResponseData("","","","")){apiWorker.selectFavorite(id)}
        emit(result)
    }



    fun createNote(id:String,request: NoteRequest) = flow{
        val result=handleOrDefault(NoteResponse()){apiWorker.createNote(id,request)}
        emit(result)
    }

    fun updateNote(id:String,request:NoteRequest) = flow{
        val result=handleOrDefault(NoteResponse()){apiWorker.updateNote(id,request)}
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