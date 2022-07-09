package com.expostore.ui.fragment.product.addproduct

import com.expostore.api.ApiWorker
import com.expostore.api.request.ProductRequest
import com.expostore.api.request.ProductUpdateRequest
import com.expostore.api.response.ProductResponse
import com.expostore.api.response.ProductResponseUpdate
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductInteractor @Inject constructor( private val apiWorker: ApiWorker) :BaseInteractor(){

    fun createProduct(id:String,request: ProductUpdateRequest) = flow{
        val result=handleOrDefault(ProductResponseUpdate()){apiWorker.createProduct(id, request)}
        emit(result)}

    fun updateProduct(id:String,request: ProductUpdateRequest)= flow {
        val result=handleOrDefault(ProductResponseUpdate()){apiWorker.updateProduct(id, request)}
        emit(result)
    }
    fun putToDraft(id:String,request: ProductUpdateRequest)= flow {
        val result=handleOrDefault(ProductResponseUpdate()){apiWorker.saveToDraft(id, request)}
        emit(result)
    }




}