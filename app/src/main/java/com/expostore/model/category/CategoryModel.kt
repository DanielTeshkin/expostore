package com.expostore.model.category

import com.expostore.api.pojo.getcategory.Category

/**
 * @author Fedotov Yakov
 */
data class CategoryModel(
    val id: String = "",
    val name: String = "",
    val products: List<CategoryProductModel> = emptyList(),
    val count: Int?
)

val Category.toModel: CategoryModel
    get() = CategoryModel(
        id ?: "",
        name ?: "",
        products.orEmpty().map { it.toModel },
        count ?: 0
    )