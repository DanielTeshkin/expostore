package com.expostore.ui.fragment.category

import com.expostore.model.product.ProductModel

interface OnClickListener {
    fun onClickLike(id:String):Unit?
    fun onClickProduct(model: ProductModel)
    fun onClickMessage(model: ProductModel):Unit?
    fun onClickCall(phone:String)
    fun onClickAnother(model: ProductModel)
}