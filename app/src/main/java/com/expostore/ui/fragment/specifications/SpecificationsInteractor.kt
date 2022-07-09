package com.expostore.ui.fragment.specifications

import com.expostore.api.ApiWorker
import com.expostore.db.LocalWorker
import com.expostore.model.category.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpecificationsInteractor @Inject constructor( private val apiWorker: ApiWorker,
                                                     private val localWorker: LocalWorker) : BaseInteractor() {


    fun getCategories() =flow {
               val result=  handleOrEmptyList { apiWorker.getProductCategory() }
                  emit(result.map { it.toModel })
              }

}