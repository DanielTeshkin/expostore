package com.expostore.model.review

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getreviews.Review
import com.expostore.model.chats.DataMapping.ImageChat
import com.expostore.model.chats.DataMapping.toModel
import com.expostore.model.product.AuthorModel
import com.expostore.model.product.ProductModel
import com.expostore.model.product.ShopModel
import com.expostore.model.product.toModel

import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewModel( val id: String?,
                        val images: List<ImageChat>?,
                        val rating: String?,
                        val promotion:String?,
                        val shop:ShopModel?,
                        val productModel: ProductModel?,
                        val text: String?,
                        val dateCreated: String?,
                        val author: AuthorModel):Parcelable

val Review.toModel:ReviewModel
get() = ReviewModel(
    id,
    images?.map { it.toModel },
    rating,
    promotion,
    shop?.toModel,
    productResponse?.toModel,
    text,
    dateCreated,
    author.toModel
)

