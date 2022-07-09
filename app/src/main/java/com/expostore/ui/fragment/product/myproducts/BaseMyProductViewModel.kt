package com.expostore.ui.fragment.product.myproducts

import com.expostore.ui.base.BaseViewModel

class BaseMyProductViewModel() :BaseViewModel() {
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun navigate(){
        navigationTo(MyProductsFragmentDirections.actionMyProductsFragmentToEditMyProduct())
    }
}