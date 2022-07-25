package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.createtender.CreateTenderRequestData
import com.expostore.api.pojo.createtender.CreateTenderResponseData
import com.expostore.api.pojo.gettenderlist.TenderPage
import com.expostore.api.pojo.gettenderlist.TenderRequest
import com.expostore.api.pojo.gettenderlist.TenderResponse
import com.expostore.api.request.CharacteristicFilterRequest
import com.expostore.db.LocalWorker
import com.expostore.model.tender.toModel
import com.expostore.ui.base.BaseInteractor
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.filter.models.toRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TenderRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker):BaseRepository() {

    fun loadMyTenders()=flow{
        val result=handleOrDefault(TenderPage()) {  apiWorker.getMyTenders()}
                  emit(result.results!!.map { it.toModel })

    }

  suspend  fun getTenders(page: Int?,
                         filterModel: FilterModel)=apiWorker.getTenders(page, filterModel.toRequest)
     fun createTender(createTenderRequestData:TenderRequest)= flow {
         val result=handleOrDefault(TenderResponse()){apiWorker.createTender(createTenderRequestData)}
         emit(result)
     }

     suspend fun getToken()=localWorker.getToken()




}