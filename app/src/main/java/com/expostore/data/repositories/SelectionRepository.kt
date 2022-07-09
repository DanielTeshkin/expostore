package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.request.ProductsSelection
import com.expostore.api.request.SelectionRequest
import com.expostore.api.response.SelectionResponse
import com.expostore.db.LocalWorker
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
     fun addProductToSelection(id: String,products:ProductsSelection)= flow {
         val result=handleOrDefault(SelectionResponse()){apiWorker.addProductToUserSelection(id, products)}
         emit(result)
     }
}