package com.expostore.ui.fragment.product.myproducts.edit

import android.util.Log
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.product.ProductInteractor

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditMyProductViewModel @Inject constructor(private val interactor: ProductInteractor): BaseViewModel() {
          private val _takeOff=MutableSharedFlow<ResponseState<ProductResponse>>()
    val taleOff=_takeOff.asSharedFlow()
    private val _product=MutableStateFlow(ProductModel())
    val product=_product.asStateFlow()
    val textButton= MutableStateFlow("Cнять с публикации")
    val buttonVisible=MutableStateFlow(true)

    override fun onStart() {
       Log.i("ddd","fff")
    }

    fun changeStatusPublished(){
        when(product.value.status){
            "draft" -> interactor.publishedProduct(product.value.id).handleResult(_takeOff,{
                navigateToProduct()
            })
            else -> takeOffProduct()
        }
    }
   private fun takeOffProduct()=interactor.takeOff(product.value.id).handleResult(_takeOff,{navigateToProduct()})
    fun navigateToBack()=navController.popBackStack()

    fun saveProductInformation(model: ProductModel){
        _product.value=model
        if (model.status=="draft") textButton.value="Опубликовать"
        if (model.status=="blocked") buttonVisible.value=false
    }

    fun navigateToProduct(){
        navigationTo(EditMyProductFragmentDirections.actionEditMyProductToMyProductsFragment())
    }
    fun navigateToAddProduct(){
        navigationTo(EditMyProductFragmentDirections.actionEditMyProductToAddProductFragment(product.value))
    }
}