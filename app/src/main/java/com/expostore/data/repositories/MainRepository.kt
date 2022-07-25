package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.db.LocalWorker
import com.expostore.db.enities.selection.toModel
import com.expostore.db.enities.toModel
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.category.toModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker) :BaseRepository(){

    fun getSelections()= operator(
        databaseQuery = {localWorker.getSelection().map { it.toModel }},
        networkCall ={ handleOrEmptyList { apiWorker.getCategories() }.map { it.toModel }},
        saveCallResult = {localWorker.saveSelections(it)}
    )


   fun getAdvertising()=flow {
    val result=  handleOrEmptyList { apiWorker.getCategoryAdvertising() }.map { it.toModel }
      emit(result)
  }


   suspend fun networkCallSelection()= handleOrEmptyList { apiWorker.getCategories() }.map { it.toModel }
    suspend fun localCallSelection() = localWorker.getSelection().map { it.toModel }
    suspend fun saveSelection(list:List<SelectionModel>) = localWorker.saveSelections(list)
    suspend fun networkCallAdvertising()= handleOrEmptyList { apiWorker.getCategoryAdvertising() }.map { it.toModel }
    suspend fun localCallAdvertising()=localWorker.getAdvertising().map { it.toModel }
    suspend fun saveAdvertising(list:List<CategoryAdvertisingModel>)=localWorker.saveAdvertising(list)
}