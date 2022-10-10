package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.gettenderlist.TenderPage
import com.expostore.data.remote.api.pojo.gettenderlist.TenderRequest
import com.expostore.data.remote.api.pojo.gettenderlist.TenderResponse
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.local.db.enities.MyProductsDao
import com.expostore.data.local.db.enities.MyTendersDao
import com.expostore.data.remote.api.base.BaseListResponse
import com.expostore.data.remote.api.pojo.gettenderlist.Tender
import com.expostore.model.product.toModel
import com.expostore.model.tender.toModel
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.filter.models.toRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TenderRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker):BaseRepository() {

    fun loadMyTenders(status:String)=flow{
        val result=handleOrDefault(TenderPage()) {  apiWorker.getMyTenders(status)}
                  emit(result.results!!.map { it.toModel }) }
    fun load(status: String) = singleOperator(
        databaseQuery = {localWorker.getMyTenders(status)},
        mapper = {it.items},
        networkCall = { handleOrDefault(TenderPage()) { apiWorker.getMyTenders(status) }.results!!.map { it.toModel }},
        saveCallResult = {localWorker.saveMyTenders(MyTendersDao(status,it))}

    )

    fun getTender(id:String)=flow{
        val result= handleOrDefault(Tender()){apiWorker.getTender(id)}.toModel
        emit(result)
    }

     suspend  fun getTenders(page: Int?, filterModel: FilterModel)=apiWorker.getTenders(page, filterModel.toRequest)

    fun createTender(createTenderRequestData:TenderRequest)= flow {
         val result=handleOrDefault(TenderResponse()){apiWorker.createTender(createTenderRequestData)}
         emit(result)
     }

    fun updateTender(id:String,request: TenderRequest)= flow {
        val result=handleOrDefault(TenderResponse()){apiWorker.updateTender(id, request)}
        emit(result)
    }

    fun takeOff(id: String)= flow{
                val result=handleOrDefault(TenderResponse()){apiWorker.takeOffTender(id)}
        emit(result)
    }

    fun publishedTender(id:String)= flow {
        emit(handleOrDefault(TenderResponse()){apiWorker.publishedTender(id)})
    }

     suspend fun getToken()=localWorker.getToken()




}