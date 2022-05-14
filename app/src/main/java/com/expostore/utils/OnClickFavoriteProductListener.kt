package com.expostore.utils

import com.expostore.model.product.ProductModel

interface OnClickFavoriteProductListener {
    fun onClickProduct(model:ProductModel)
    fun onClickLike(id: String)
}