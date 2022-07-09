package com.expostore.data.repositories

import android.util.Log
import com.expostore.api.ApiWorker
import com.expostore.api.base.BaseApiResponse
import com.expostore.api.base.BaseListResponse
import com.expostore.api.request.FilterRequest

import com.expostore.api.request.toRequestModel
import com.expostore.api.request.toStroke
import com.expostore.api.response.ProductResponse
import com.expostore.db.LocalWorker
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.product.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepository @Inject constructor( private val apiWorker: ApiWorker, private val localWorker: LocalWorker):
    BaseRepository() {

  suspend fun getProducts(

    page: Int? = null,
    q:String?=null,
    lat: Double?=null,
    long: Double?=null,
    city:String?=null,
    sort:String?=null,
    category: String?=null,
    price_min: Int?=null,
    price_max: Int?=null,
    promotion: Boolean?=null,
    characteristics: List<CharacteristicFilterModel>? =null
    ): BaseApiResponse<BaseListResponse<ProductResponse>>{
 val c=characteristics?.map {

   it.toRequestModel.toString()
  }

    return apiWorker.getListProduct(
     page, q, lat, long, city, sort,category, price_min, price_max, promotion, c?.get(0)
    )}


  fun load(status: String) = flow {
    val result =
      handleOrDefault(BaseListResponse<ProductResponse>()) { apiWorker.getMyListProduct(status) }.results
    emit(result.map { it.toModel })
  }

  fun takeOff(id: String) = flow {
    val result = handleOrDefault(ProductResponse()) { apiWorker.takeOffProduct(id) }
    emit(result)
  }
    fun testGetProduct(


                        filter:FilterRequest?=null)=flow{
        val result=handleOrDefault(BaseListResponse()){ apiWorker.getProducts(0,filter)}
        emit(result.results)
    }

}