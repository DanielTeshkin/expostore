package com.expostore.model.review

import android.os.Parcelable
import com.expostore.data.remote.api.response.ReviewsResponse

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reviews(
    val my_reviews:List<ReviewModel>?,
   val reviews_for_user:List<ReviewModel>?
): Parcelable
val ReviewsResponse.toModel:Reviews
    get() = Reviews(
        my_reviews?.map {it.toModel},
        reviews_for_user?.map {it.toModel}
    )
