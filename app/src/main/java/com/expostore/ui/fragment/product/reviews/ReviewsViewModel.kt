package com.expostore.ui.fragment.product.reviews

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.model.review.Reviews
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject



@HiltViewModel
class ReviewsViewModel @Inject constructor(private val interactor: ReviewsInteractor) : BaseViewModel() {
    private val _reviews=MutableSharedFlow<ResponseState<Reviews>>()
         val reviews=_reviews.asSharedFlow()
    private val _who= MutableStateFlow("")
    val who=_who.asStateFlow()

    fun load(){
        interactor.getReviews().handleResult(_reviews)
    }

    fun whoUpdate(param:String){
        _who.value=param
    }

    fun navigate(){
          navigationTo(ReviewsFragmentDirections.actionReviewsFragmentToProfileFragment())
    }


    override fun onStart() {
        TODO("Not yet implemented")
    }


}