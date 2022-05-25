package com.expostore.ui.fragment.product.reviews

import com.expostore.api.ApiWorker
import com.expostore.api.response.ReviewsResponse

import com.expostore.model.review.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReviewsInteractor @Inject constructor (private val apiWorker: ApiWorker) :BaseInteractor(){

    fun getReviews()=flow{
        val result=handleOrDefault(ReviewsResponse()){apiWorker.getReviews()}.toModel
        emit(result)
    }


}