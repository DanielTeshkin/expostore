package com.expostore.model.category

import android.os.Parcelable
import com.expostore.api.response.CategoryResponse
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryModel(
    val name: String,
    val count: Int,
    val id: String,
    val products: List<ProductModel>,
    val date_create: String? = null
): Parcelable

val CategoryResponse.toModel: CategoryModel
    get() = CategoryModel(
        name ?: "",
        count ?: 0,
        id ?: "",
        products.orEmpty().map { it.toModel },
        date_create
    )