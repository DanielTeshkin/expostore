package com.expostore.model.favorite

import android.os.Parcelable
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.api.response.ProductResponse
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteProduct(
   val id: String?,
     val product: ProductModel,
    val notes: String?,
    val user: String?
): Parcelable

val GetFavoritesListResponseData.toModel:FavoriteProduct
        get() = FavoriteProduct(
            id=id,
            product=product.toModel,
            notes=notes,
            user=user
        )