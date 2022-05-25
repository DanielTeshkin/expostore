package com.expostore.ui.fragment.product.myproducts

import androidx.paging.PagingData
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
@HiltViewModel
class DraftViewModel @Inject constructor(private val repository:MyProductsRepository) :BaseViewModel(){
    private val _list= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val list= _list.asSharedFlow()
    fun navigate(){
        navigationTo(MyProductsFragmentDirections.actionMyProductsFragmentToProductFragment())
    }
    fun loadDraftProduct()=repository.load("draft").handleResult(_list)
    override fun onStart() {
        TODO("Not yet implemented")
    }
}