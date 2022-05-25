package com.expostore.ui.fragment.shop.shopcreate

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.addshop.AddShopRequestData
import com.expostore.api.response.ShopResponse
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InteractorShopCreate@Inject constructor(private val apiWorker: ApiWorker):BaseInteractor() {
    fun shopCreate(request: AddShopRequestData)=flow{
        val result=handleOrDefault(ShopResponse()){apiWorker.shopCreate(request)}
        emit(result)
    }

    fun editShop(request: AddShopRequestData)=flow{
        val result=handleOrDefault(ShopResponse()){apiWorker.editShop(request)}
        emit(result)
    }

    fun getMyShop()=flow{
        emit(apiWorker.getMyShop())
    }

}