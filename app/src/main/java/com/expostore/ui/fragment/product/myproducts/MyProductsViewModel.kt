package com.expostore.ui.fragment.product.myproducts



import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProductsViewModel @Inject constructor(private val repository: MyProductsRepository) : BaseViewModel() {

    private val _list= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val list= _list.asSharedFlow()

    fun loadMyProduct(){
        repository.load("my").handleResult(_list)
    }

    fun navigate(){
        navigationTo(MyProductsFragmentDirections.actionMyProductsFragmentToProductFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}