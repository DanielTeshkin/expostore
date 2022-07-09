package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.model.category.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val apiWorker: ApiWorker):BaseRepository() {

    fun getCategoryCharacteristic(id:String) = flow {
       val result= handleOrEmptyList { apiWorker.getCategoryCharacteristic(id) }.map { it.toModel }
        emit(result)
    }
}