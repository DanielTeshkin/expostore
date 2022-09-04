package com.expostore.data.remote.api.response

import com.expostore.data.remote.api.pojo.getreviews.Review
import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
     @SerializedName("my_reviews")  val my_reviews:List<Review>? = listOf(),
     @SerializedName("reviews_for_user") val reviews_for_user:List<Review>? = listOf()

 )
