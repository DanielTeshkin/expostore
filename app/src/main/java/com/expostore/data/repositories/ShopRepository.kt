package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.addshop.AddShopRequestData
import com.expostore.data.remote.api.pojo.getshop.GetShopResponseData
import com.expostore.data.remote.api.response.ShopResponse
import com.expostore.data.local.db.LocalWorker
import com.expostore.model.product.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopRepository  @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker):BaseRepository() {
    fun getMyShop()=flow{
        val result= handleOrDefault(ShopResponse()){apiWorker.getMyShop()}
        emit(result.toModel)
    }
    fun shopCreate(request: AddShopRequestData)=flow{
        val result=handleOrDefault(ShopResponse()){apiWorker.shopCreate(request)}
        emit(result)
    }

    fun editShop(request: AddShopRequestData)=flow{
        val result=handleOrDefault(ShopResponse()){apiWorker.editShop(request)}
        emit(result)
    }
    fun getShop(id:String)= flow {
        val result=handleOrDefault(GetShopResponseData()){apiWorker.getShop(id)}
        emit(result)
    }

}