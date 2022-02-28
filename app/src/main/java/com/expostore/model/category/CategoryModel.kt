package com.expostore.model.category

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.Category
import kotlinx.android.parcel.Parcelize

/**
 * @author Fedotov Yakov
 */
@Parcelize
data class CategoryModel(
    val id: String = "",
    val name: String = "",
    val products: List<CategoryProductModel> = emptyList(),
    val count: Int?
): Parcelable

val Category.toModel: CategoryModel
    get() = CategoryModel(
        id ?: "",
        name ?: "",
        products.orEmpty().map { it.toModel },
        count ?: 0
    )