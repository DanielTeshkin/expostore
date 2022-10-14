package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.local.db.LocalWorker
import com.expostore.model.category.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository  @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker) :BaseRepository() {

    fun getCategoryCharacteristic(id:String) = flow {
       val result= handleOrEmptyList { apiWorker.getCategoryCharacteristic(id) }.map { it.toModel }
        emit(result)
    }
    fun getCategories() =operator(
       databaseQuery = { localWorker.getCategories().map { it.toModel }},
        networkCall = {handleOrEmptyList { apiWorker.getProductCategory() }.map { it.toModel }},
        clearCall = {localWorker.removeCategories()},
        saveCallResult = {localWorker.saveCategories(it)}

    )


}