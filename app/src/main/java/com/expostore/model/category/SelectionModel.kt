package com.expostore.model.category

import android.os.Parcelable

import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectionModel(
    val name: String ="",
    val count: Int =0,
    val id: String ="",
    val products: List<ProductModel> = listOf(),
    val date_create: String? = null
): Parcelable

val SelectionResponse.toModel: SelectionModel
    get() = SelectionModel(
        name ?: "",
        count ?: 0,
        id ?: "",
        products.orEmpty().map { it.toModel },
        date_create
    )