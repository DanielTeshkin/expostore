package com.expostore.ui.fragment.favorites

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.response.CategoryResponse
import com.expostore.api.response.SaveSearchResponse
import com.expostore.model.SaveSearchModel
import com.expostore.model.category.CategoryModel
import com.expostore.model.category.toModel
import com.expostore.model.favorite.toModel
import com.expostore.model.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val apiWorker: ApiWorker): BaseInteractor() {
    fun getFavoritesList() = flow{
        val result=handleOrEmptyList { apiWorker.getFavoritesList() }.map { it.toModel }
        emit(result)
    }
    fun updateSelected(id:String)=flow{
        val result=handleOrDefault(SelectFavoriteResponseData("","","","")){apiWorker.selectFavorite(id)}
        emit(result)
    }

    fun userSelectionList()= flow {
        val result=handleOrEmptyList { apiWorker.getUserSelection() }.map { it.toModel }
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

    fun deleteUserSelection(id:String) = flow {
        val result=handleOrDefault(CategoryResponse()){apiWorker.deleteUserSelection(id)}
        emit(result)
    }


}