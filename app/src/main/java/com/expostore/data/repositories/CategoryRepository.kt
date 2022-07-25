package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.db.LocalWorker
import com.expostore.model.category.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository  @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker) :BaseRepository() {

    fun getCategoryCharacteristic(id:String) = flow {
       val result= handleOrEmptyList { apiWorker.getCategoryCharacteristic(id) }.map { it.toModel }
        emit(result)
    }
    fun getCategories() =flow {
        val result=  handleOrEmptyList { apiWorker.getProductCategory() }
        emit(result.map { it.toModel })
    }
}