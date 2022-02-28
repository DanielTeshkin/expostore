package com.expostore.model.category

import com.expostore.api.pojo.getcategoryadvertising.CategoryAdvertising

/**
 * @author Fedotov Yakov
 */
data class CategoryAdvertisingModel(
    val id: String = "",
    val image: String = "",
    val url: String = "",
    val dateCreated: String = ""
)

val CategoryAdvertising.toModel: CategoryAdvertisingModel
    get() = CategoryAdvertisingModel(
        id ?: "",
        image ?: "",
        url ?: "",
        dateCreated ?: ""
    )