package com.expostore.ui.fragment.product.myproducts.adapter

import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.product.myproducts.OnClickMyProduct
import kotlinx.coroutines.flow.MutableStateFlow

class MyProductClickRepository private constructor(){
     private var onClickMyProduct:MutableStateFlow<OnClickMyProduct>?=null

    fun getClickMyProduct(): MutableStateFlow<OnClickMyProduct>? {
        return onClickMyProduct
    }

    companion object {
        private val mInstance: MyProductClickRepository = MyProductClickRepository()
        @Synchronized
        fun getInstance(): MyProductClickRepository {
            return mInstance
        }
    }
}