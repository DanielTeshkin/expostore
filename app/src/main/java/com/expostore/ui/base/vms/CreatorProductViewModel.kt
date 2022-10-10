package com.expostore.ui.base.vms

import android.util.Log
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.request.createProductRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.ui.base.interactors.CreateProductInteractor
import kotlinx.coroutines.flow.MutableStateFlow

abstract class CreatorProductViewModel :BaseCreatorViewModel<CreateResponseProduct, ProductResponse, ProductUpdateRequest>() {
    abstract override val interactor:CreateProductInteractor
    override val itemId: ItemId<CreateResponseProduct>
    get() = {it-> it.id?:""}
    val shortDescription= MutableStateFlow("")
    override fun createRequest()= createProductRequest(
        count.value.toIntOrNull(),
        name.value, price.value,
        longDescription.value,
        shortDescription.value,
        imageList.value,
        connectionType.value,
        characteristicLoad(),
        category.value
        )

    fun changeShortDescription(text:String){
        Log.i("disc",text)
        shortDescription.value=text
        checkEnabled()
    }

}