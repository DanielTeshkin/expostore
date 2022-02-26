package com.expostore.model.category

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.getcategory.CategoryProductImage

/**
 * @author Fedotov Yakov
 */
data class CategoryProductModel(
    val id: String = "",
    val name: String = "",
    val images: List<CategoryProductImageModel> = emptyList(),
    val price: String = "",
    val rating: String = "",
    val promotion: String = "",
    val like: Boolean = false
)

val CategoryProduct.toModel: CategoryProductModel
    get() = CategoryProductModel(
        id ?: "",
        name ?: "",
        images.orEmpty().map { it.toModel },
        price ?: "",
        rating ?: "",
        promotion ?: "",
        like
    )