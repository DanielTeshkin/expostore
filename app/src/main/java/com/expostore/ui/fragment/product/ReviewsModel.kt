package com.expostore.ui.fragment.product

import android.os.Parcelable
import com.expostore.model.review.ReviewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewsModel(
    val reviews: List<ReviewModel>
):Parcelable
