package com.expostore.ui.fragment.product.myproducts.tabs

import androidx.lifecycle.ViewModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.product.ProductInteractor
import com.expostore.ui.fragment.product.myproducts.MyProductsFragmentDirections
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class TabMyProductViewModel @Inject constructor(private val interactor: ProductInteractor) : BaseViewModel() {
    private val _list= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val list= _list.asSharedFlow()

    fun loadMyProduct(status:String){
       interactor.load(status).handleResult(_list)
    }

    fun navigate(productModel: ProductModel){
        navigationTo(MyProductsFragmentDirections.actionMyProductsFragmentToEditMyProduct(productModel))
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}