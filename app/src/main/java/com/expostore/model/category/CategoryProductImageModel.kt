package com.expostore.model.category

import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.expostore.api.pojo.signin.SignInResponseData

/**
 * @author Fedotov Yakov
 */
data class CategoryProductImageModel(
    val id: String = "",
    val file: String = ""
)

val CategoryProductImage.toModel: CategoryProductImageModel
    get() = CategoryProductImageModel(
        id ?: "",
        file ?: ""
    )