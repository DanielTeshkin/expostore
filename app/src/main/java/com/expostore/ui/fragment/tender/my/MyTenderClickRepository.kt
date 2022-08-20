package com.expostore.ui.fragment.tender.my

import com.expostore.ui.fragment.product.myproducts.OnClickMyProduct
import com.expostore.ui.fragment.product.myproducts.adapter.MyProductClickRepository
import kotlinx.coroutines.flow.MutableStateFlow

class MyTenderClickRepository private constructor(){
    private var onClickMyTender: MutableStateFlow<OnClickMyTender>?=null

    fun getClickMyProduct(): MutableStateFlow<OnClickMyTender>? {
        return onClickMyTender
    }

    companion object {
        private val mInstance: MyTenderClickRepository = MyTenderClickRepository()
        @Synchronized
        fun getInstance(): MyTenderClickRepository {
            return mInstance
        }
    }
}