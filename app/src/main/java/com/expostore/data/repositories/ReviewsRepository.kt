package com.expostore.data.repositories

import android.media.Rating
import com.expostore.api.ApiWorker
import com.expostore.api.pojo.addreview.AddReviewRequestData
import com.expostore.api.pojo.addreview.AddReviewResponseData
import com.expostore.api.response.ReviewsResponse
import com.expostore.model.review.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReviewsRepository @Inject constructor(private val apiWorker:ApiWorker):BaseRepository() {
    fun getReviews()= flow {
        val result=handleOrDefault(ReviewsResponse()){apiWorker.getReviews()}.toModel
        emit(result)
    }
    fun addReview(id:String,images:List<String>,rating: Int,text:String) = flow {
        val result=handleOrDefault(AddReviewResponseData()){apiWorker.addReview(id,AddReviewRequestData(rating, text, images))}
        emit(result)
    }
}