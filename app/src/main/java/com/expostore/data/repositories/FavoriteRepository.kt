package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.data.remote.api.request.NoteRequest

import com.expostore.data.remote.api.response.NoteResponse
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.remote.api.base.BaseListResponse
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender

import com.expostore.model.favorite.toModel
import com.expostore.model.product.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker): BaseRepository() {
    fun getFavoritesList() = flow{
        val result=handleOrEmptyList { apiWorker.getFavoritesList() }.map { it.toModel }
        emit(result)
    }
    fun getFavorites()=operator(
        databaseQuery = {localWorker.getFavoritesProduct().map { FavoriteProduct(it.id,it.product,it.notes,it.user) }},
        networkCall = {handleOrEmptyList { apiWorker.getFavoritesList() }.map { it.toModel }},
        clearCall = {localWorker.removeFavorites()},
        saveCallResult = {localWorker.saveFavorites(it)}
    )
    fun addToFavorite(id:String, noteRequest: NoteRequest=NoteRequest())=flow{
        val result=handleOrDefault(SelectFavoriteResponseData()){apiWorker.selectFavorite(id,noteRequest)}
        emit(result)
    }

    fun getFavoriteTenderList() = flow{
        val result=handleOrEmptyList { apiWorker.getFavoritesTenderList() }.map { it.toModel }
        emit(result)
    }
    fun getFavoriteTenders()=operator(
        databaseQuery = {localWorker.getFavoritesTender().map { FavoriteTender(it.id,it.tender,it.notes,it.user) }},
        networkCall = {handleOrEmptyList { apiWorker.getFavoritesTenderList() }.map { it.toModel }},
        clearCall = {localWorker.removeFavoritesTender()},
        saveCallResult = {localWorker.saveFavoritesTender(it)}
    )

    fun addToFavoriteTender(id:String, noteRequest: NoteRequest= NoteRequest())=flow{
        val result=handleOrDefault(SelectFavoriteTenderResponseData("","","","")){apiWorker.selectFavoriteTender(id,noteRequest)}
        emit(result)
    }
    fun addNoteProduct(id:String,  noteRequest: NoteRequest) = flow{
                 val result=handleOrDefault(SelectFavoriteResponseData()){apiWorker.updateFavoriteProduct(id,noteRequest) }
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