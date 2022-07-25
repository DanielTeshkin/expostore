package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.response.ShopResponse
import com.expostore.db.LocalWorker
import com.expostore.model.product.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopRepository  @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker):BaseRepository() {
    fun getMyShop()=flow{
        val result= handleOrDefault(ShopResponse()){apiWorker.getMyShop()}
        emit(result.toModel)
    }

}