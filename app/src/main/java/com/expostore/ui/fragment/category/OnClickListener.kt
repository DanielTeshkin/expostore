package com.expostore.ui.fragment.category

import com.expostore.model.product.ProductModel

interface OnClickListener {
    fun onClickLike(id:String)
    fun onClickProduct(model: ProductModel)
    fun onClickMessage(model: ProductModel)
    fun onClickCall(phone:String)
}