package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.createtender.CreateTenderRequestData
import com.expostore.api.pojo.createtender.CreateTenderResponseData
import com.expostore.api.pojo.gettenderlist.TenderPage
import com.expostore.api.request.CharacteristicFilterRequest
import com.expostore.db.LocalWorker
import com.expostore.model.tender.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TenderRepository @Inject constructor( private val apiWorker: ApiWorker,
                                            private val localWorker: LocalWorker) :BaseRepository() {

    fun loadMyTenders()=flow{
        val result=handleOrDefault(TenderPage()) {  apiWorker.getMyTenders()}
                  emit(result.results!!.map { it.toModel })

    }

  suspend  fun getTenders(page: Int?,
                          name:String?=null,
                          lat:Double?=null,
                          long:Double?=null,
                          distance:Double?=null,
                          sort:String?=null,
                          category:String?=null,
                          price_max:String?=null,
                   price_min:String?,
  city:String?=null,
                          promotion: Boolean?=null,
                          characteristics: List<String>?=null)=apiWorker.getTenders(page, name, lat, long, distance, sort, category, price_max, price_min,city,promotion,characteristics)
     fun createTender(createTenderRequestData: CreateTenderRequestData)= flow {
         val result=handleOrDefault(CreateTenderResponseData()){apiWorker.createTender(createTenderRequestData)}
         emit(result)
     }

     suspend fun getToken()=localWorker.getToken()




}