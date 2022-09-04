package com.expostore.utils

import com.expostore.data.remote.api.pojo.getcategory.Category

interface OnClickRecyclerViewListener {
    fun onLikeClick(like: Boolean, id: String?)

    fun onDetailCategoryProductItemClick(id:String?)

    fun onChatClick()


    //Main Fragment
    fun onDetailCategoryButton(category: Category)
    fun onProductClick(id: String?)

}