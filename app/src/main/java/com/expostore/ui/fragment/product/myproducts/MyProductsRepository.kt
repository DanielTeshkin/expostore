package com.expostore.ui.fragment.product.myproducts

import androidx.paging.*
import com.expostore.api.ApiWorker
import com.expostore.api.base.BaseListResponse
import com.expostore.api.response.ProductResponse
import com.expostore.model.product.toModel
import com.expostore.ui.base.BaseInteractor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyProductsRepository @Inject constructor(private val apiWorker: ApiWorker) :BaseInteractor(){
    fun load(status:String)=flow{
        val result=handleOrDefault( BaseListResponse <ProductResponse>()) { apiWorker.getMyListProduct(status) }.results
        emit(result!!.map { it.toModel })

    }


}