package com.expostore.ui.fragment.product.addproduct

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveFileResponseData

import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.BaseCreatorViewModel
import com.expostore.ui.fragment.category.personal.create.CreatePersonalSelectionFragmentDirections
import com.expostore.ui.fragment.product.ProductInteractor
import com.expostore.ui.fragment.product.personal.create.CreatePersonalProductFragment
import com.expostore.ui.fragment.product.personal.create.CreatePersonalProductFragmentDirections
import com.expostore.ui.general.*
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel
@Inject constructor(private val addProductInteractor: ProductInteractor) : BaseCreatorViewModel() {
    private val _addProduct = MutableSharedFlow<ResponseState<CreateResponseProduct>>()
    val addProduct = _addProduct.asSharedFlow()
    private val _product = MutableStateFlow(ProductModel())
    val product = _product.asStateFlow()
    private val _updateProduct = MutableSharedFlow<ResponseState<CreateResponseProduct>>()
    private val _productPublic = MutableSharedFlow<ResponseState<ProductResponse>>()
    val productPublic = _productPublic.asSharedFlow()
    private val shopId = MutableStateFlow("")
    private val _saveFile= MutableStateFlow<ResponseState<SaveFileResponseData>>(ResponseState.Loading(false))
    val saveFile= _saveFile.asStateFlow()
     init {
         loadCategories()
     }

    fun saveFile(uris: List<SaveFileRequestData>)=
        addProductInteractor
            .saveFile(uris)
            .handleResult(_saveFile)

    fun saveProductInformation(product: ProductModel) {
        shopId.value = product.shop.id
        addProductInteractor.saveCharacteristic(product.characteristics)
        flag.value = true
    }

    private fun published(id: String) = addProductInteractor
        .publishedProduct(id)
        .handleResult(_productPublic)

    fun createOrUpdate(status: String,request: ProductUpdateRequest) {
        when (flag.value) {
            true -> {
                if (status == "my") addProductInteractor.updateProduct(product.value.id, request)
                    .handleResult(_updateProduct, {
                        published(it.id ?: "")
                    })
                else addProductInteractor.updateProduct(product.value.id, request)
                    .handleResult(_updateProduct)
            }
            false -> createProduct(status)
        }
    }

    private fun createProduct(status: String) {
        when (status) {
            "my" -> addProductInteractor.createProduct().handleResult(_addProduct, {
                published(it.id ?: "")
            })
            else -> addProductInteractor.createProduct().handleResult(_addProduct)
        }
    }
    fun createPersonalProduct(context: Context) {}

    fun saveShopId(id: String){
        shopId.value=id
    }
    override fun onStart() {
    }
}