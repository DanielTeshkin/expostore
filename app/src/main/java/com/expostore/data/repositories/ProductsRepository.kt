package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.base.BaseApiResponse
import com.expostore.data.remote.api.base.BaseListResponse
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct

import com.expostore.data.remote.api.response.ProductResponse

import com.expostore.data.local.db.LocalWorker
import com.expostore.data.remote.api.response.PersonalProductRequest
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.filter.models.toRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker): BaseRepository() {
    suspend fun getProducts(page: Int? = null, filterModel: FilterModel)= apiWorker
        .getListProduct(page, filterModel.toRequest)

    fun getBaseListProducts()=flow{
        val result= handleOrDefault(BaseListResponse()){apiWorker.getProducts()}.results.map { it.toModel }
        emit(result)
    }
    fun getProduct(id:String)=flow {
        val result = handleOrDefault(ProductResponse()) { apiWorker.getProduct(id) }.toModel
        emit(result)
    }


  fun load(status: String) = flow {
    val result =
      handleOrDefault(BaseListResponse()) { apiWorker.getMyListProduct(status) }.results
    emit(result.map { it.toModel })
  }

  fun takeOff(id: String) = flow {
    val result = handleOrDefault(ProductResponse()) { apiWorker.takeOffProduct(id) }
    emit(result)
  }
    fun createProduct(id:String,request: ProductUpdateRequest) = flow{
        val result=handleOrDefault(CreateResponseProduct()){apiWorker.createProduct(id, request)}
        emit(result)}

    fun updateProduct(id:String,request: ProductUpdateRequest)= flow {
        val result=handleOrDefault(CreateResponseProduct()){apiWorker.updateProduct(id, request)}
        emit(result)
    }

    fun publishedProduct(id:String)= flow {
        val result=handleOrDefault(ProductResponse()){apiWorker.publishedProduct(id)}
        emit(result)
    }

    fun getPersonalProducts()=flow{
        val result= handleOrDefault(BaseListResponse()){apiWorker.getPersonalProducts()}.results.map { it.toModel }
        emit(result)
    }
    fun createPersonalProduct( request: ProductUpdateRequest)=flow{
        val result=handleOrDefault(CreateResponseProduct()){apiWorker.createPersonalProduct(request)}
        emit(result)
    }
    fun deletePersonalProduct(id: String)= flow {
        val result= handleOrDefault(ProductResponse()){apiWorker.deletePersonalProduct(id)}
        emit(result)
    }

    fun getPriceHistory(id:String) = flow{
        val result = handleOrEmptyList { apiWorker.getPriceHistory(id) }.map { it.toModel }
        emit(result)
    }


}