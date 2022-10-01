package com.expostore.ui.fragment.category

import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel

interface OnClickListener {
    fun onClickLike(id:String):Unit?
    fun onClickProduct(model: ProductModel)
    fun onClickMessage(model: ProductModel):Unit?
    fun onClickCall(phone:String)
    fun onClickAnother(model: ProductModel)
}
interface OnClickListeners<T> {
    fun onClickLike(id:String)
    fun onClickItem(model: T)
    fun onClickMessage(id:String)
    fun onClickCall(phone:String)
    fun onClickAnother(model: T)
}



inline fun < reified T> check(item:T){
    when(item){
        is TenderModel ->{}

    }
}