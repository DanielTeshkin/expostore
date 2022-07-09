package com.expostore.ui.fragment.product.myproducts.edit

import androidx.lifecycle.ViewModel
import com.expostore.api.response.ProductResponse
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.product.myproducts.MyProductsRepository
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditMyProductViewModel @Inject constructor(private val repository: MyProductsRepository): BaseViewModel() {
          private val _takeOff=MutableSharedFlow<ResponseState<ProductResponse>>()
    val taleOff=_takeOff.asSharedFlow()
    private val _product=MutableStateFlow<ProductModel>(ProductModel())
    val product=_product.asStateFlow()
    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun takeOffProduct(id:String)=repository.takeOff(id).handleResult(_takeOff,{navigateToProduct()})


    fun saveProductInformation(model: ProductModel){
        _product.value=model
    }

    fun navigateToProduct(){
        navigationTo(EditMyProductFragmentDirections.actionEditMyProductToMyProductsFragment())
    }
    fun navigateToAddProduct(){
        navigationTo(EditMyProductFragmentDirections.actionEditMyProductToAddProductFragment())
    }
}