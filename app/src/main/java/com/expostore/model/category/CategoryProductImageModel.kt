package com.expostore.model.category

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.expostore.api.pojo.signin.SignInResponseData
import kotlinx.android.parcel.Parcelize

/**
 * @author Fedotov Yakov
 */
@Parcelize
data class CategoryProductImageModel(
    val id: String = "",
    val file: String = ""
): Parcelable

val CategoryProductImage.toModel: CategoryProductImageModel
    get() = CategoryProductImageModel(
        id ?: "",
        file ?: ""
    )