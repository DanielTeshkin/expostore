package com.expostore.model.favorite

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.data.remote.api.response.TenderFavoriteResponse

import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.toModel

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

@Parcelize
data class FavoriteTender(
    val id: String?,
    val tender: TenderModel,
    val notes: String?,
    val user: String?
): Parcelable

val TenderFavoriteResponse.toModel:FavoriteTender
get() = FavoriteTender(id,tender.toModel,notes, user)