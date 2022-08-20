package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.base.BaseApiResponse
import com.expostore.api.base.BaseListResponse
import com.expostore.api.request.ProductUpdateRequest
import com.expostore.api.response.CreateResponseProduct

import com.expostore.api.response.ProductResponse

import com.expostore.db.LocalWorker
import com.expostore.model.product.toModel
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.filter.models.toRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker): BaseRepository() {
    suspend fun getProducts(
        page: Int? = null,
     filterModel: FilterModel
    ): BaseApiResponse<BaseListResponse<ProductResponse>> = apiWorker.getListProduct(page, filterModel.toRequest)

    fun getBaseListProducts()=flow{
        val result= handleOrDefault(BaseListResponse()){apiWorker.getProducts()}.results.map { it.toModel }
        emit(result)
    }


  fun load(status: String) = flow {
    val result =
      handleOrDefault(BaseListResponse<ProductResponse>()) { apiWorker.getMyListProduct(status) }.results
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
    fun putToDraft(id:String,request: ProductUpdateRequest)= flow {
        val result=handleOrDefault(ProductResponse()){apiWorker.saveToDraft(id, request)}
        emit(result)
    }
    fun publishedProduct(id:String)= flow {
        val result=handleOrDefault(ProductResponse()){apiWorker.publishedProduct(id)}
        emit(result)
    }

}