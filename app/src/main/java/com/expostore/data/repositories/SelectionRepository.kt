package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.remote.api.request.SelectionRequest
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.local.db.enities.selection.toModel
import com.expostore.model.category.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelectionRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker):BaseRepository() {

    fun userSelectionList()= flow {
        val result=handleOrEmptyList { apiWorker.getUserSelection() }.map { it.toModel }
        emit(result)
    }

    fun deleteUserSelection(id:String) = flow {
        val result=handleOrDefault(SelectionResponse()){apiWorker.deleteUserSelection(id)}
        emit(result.toModel)
    }

    fun createSelection(selectionRequest: SelectionRequest) = flow {
        val result= handleOrDefault(SelectionResponse()){apiWorker.createUserSelection(selectionRequest)}
        emit(result)
    }
    fun updateSelection(selectionRequest: SelectionRequest,id: String) = flow {
        val result= handleOrDefault(SelectionResponse()){apiWorker.updateUserSelection(id,selectionRequest)}
        emit(result)
    }
     fun addProductToSelection(id: String,products:ProductsSelection)= flow {
         val result=handleOrDefault(SelectionResponse()){apiWorker.addProductToUserSelection(id, products)}
         emit(result)
     }

    fun getSelections()= operator(
        databaseQuery = {localWorker.getSelection().map { it.toModel }},
        networkCall ={ handleOrEmptyList { apiWorker.getCategories() }.map { it.toModel }},
        saveCallResult = {localWorker.saveSelections(it)}
    )
}