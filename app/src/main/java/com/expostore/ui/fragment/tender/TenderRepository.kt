package com.expostore.ui.fragment.tender

import com.expostore.api.ApiWorker
import com.expostore.model.tender.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TenderRepository @Inject constructor( private val apiWorker: ApiWorker) :BaseInteractor() {

    fun loadMyTenders()=flow{
        val result=handleOrEmptyList {  apiWorker.getMyTenders()}.map { it.toModel }
                  emit(result) }
}