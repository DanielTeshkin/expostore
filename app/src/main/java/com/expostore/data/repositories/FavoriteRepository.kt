package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.api.request.NoteRequest

import com.expostore.api.response.NoteResponse
import com.expostore.api.response.SaveSearchResponse
import com.expostore.api.response.SelectionResponse
import com.expostore.db.LocalWorker

import com.expostore.model.category.toModel
import com.expostore.model.favorite.toModel
import com.expostore.model.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker): BaseRepository() {
    fun getFavoritesList() = flow{
        val result=handleOrEmptyList { apiWorker.getFavoritesList() }.map { it.toModel }
        emit(result)
    }
    fun updateSelected(id:String,noteRequest: NoteRequest=NoteRequest())=flow{
        val result=handleOrDefault(SelectFavoriteResponseData("","","","")){apiWorker.selectFavorite(id,noteRequest)}
        emit(result)
    }

    fun getFavoriteTenderList() = flow{
        val result=handleOrEmptyList { apiWorker.getFavoritesTenderList() }.map { it.toModel }
        emit(result)
    }
    fun updateSelectedTender(id:String,noteRequest: NoteRequest= NoteRequest())=flow{
        val result=handleOrDefault(SelectFavoriteTenderResponseData("","","","")){apiWorker.selectFavoriteTender(id,noteRequest)}
        emit(result)
    }
    fun addNoteProduct(id:String,  noteRequest: NoteRequest) = flow{
                 val result=handleOrDefault(SelectFavoriteResponseData("","","","")){apiWorker.updateFavoriteProduct(id,noteRequest) }
                 emit(result)
             }
    fun addNoteTender(id:String,  noteRequest: NoteRequest) = flow{
        val result=handleOrDefault(SelectFavoriteTenderResponseData("","","","")){apiWorker.updateFavoriteTender(id,noteRequest) }
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






}