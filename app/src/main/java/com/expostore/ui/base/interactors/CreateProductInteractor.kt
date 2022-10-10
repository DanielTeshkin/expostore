package com.expostore.ui.base.interactors

import com.expostore.data.remote.api.pojo.getcategory.CharacteristicRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.request.createProductRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CreateProductInteractor @Inject constructor(
    override val multimedia: MultimediaRepository,
    override val category: CategoryRepository,
    private val products:ProductsRepository
) :BaseCreatorInteractor<CreateResponseProduct,ProductResponse,ProductUpdateRequest>(){
    private val _shopId= MutableStateFlow("")
    val shopId=_shopId.asStateFlow()
    val files= mutableListOf<String?>(null,null)
    override fun published(id: String)=products.publishedProduct(id)
    override fun update(id: String, request: ProductUpdateRequest)=products.updateProduct(id, request)
    override fun create(request: ProductUpdateRequest)=products.createProduct(shopId.value,request)
    fun createPersonalProduct(request: ProductUpdateRequest) = products.createPersonalProduct(request)
    fun saveFiles(request: List<SaveFileRequestData>) = multimedia.saveFileBase64(request)
    fun getPersonalProduct(id:String)=products.getPersonalProduct(id)
    fun getProduct(id:String)=products.getProduct(id)

    fun saveShopId(id:String){
        _shopId.value=id
    }


}