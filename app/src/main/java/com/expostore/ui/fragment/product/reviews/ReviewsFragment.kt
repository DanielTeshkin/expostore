package com.expostore.ui.fragment.product.reviews

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ReviewsFragmentBinding
import com.expostore.model.review.ReviewModel
import com.expostore.model.review.Reviews
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.general.ControllerUI
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.state.ResponseState
import com.expostore.utils.OnClickImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class ReviewsFragment : BaseFragment<ReviewsFragmentBinding>(ReviewsFragmentBinding::inflate) {

    private val reviewsViewModel: ReviewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey"){_, bundle->
            val result=bundle.getString("name")
            if(result!=null) {
                reviewsViewModel.whoUpdate(result)
            }
        }
        subscribeViewModel()
    }

  private  fun subscribeViewModel(){
        reviewsViewModel.load()
        reviewsViewModel.apply {
            subscribe(reviews){handleResult(it)}
            subscribe(navigation){navigateSafety(it)}

        }
    }


    fun handleResult(state:ResponseState<Reviews>){
        when(state) {
            is ResponseState.Success -> loadReviews(state.item)
            is ResponseState.Error -> Toast.makeText(requireContext(),state.throwable.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadReviews(reviews: Reviews) {
        val list = check(reviews)
        val onClickImage=object :OnClickImage{
            override fun click(bitmap: Bitmap) {
                ControllerUI(requireContext()).openImageFragment(bitmap)
                    .show(requireActivity().supportFragmentManager, "DialogImage")
            }

        }
        binding.apply {
            rvReviews.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ReviewRecyclerViewAdapter(list,onClickImage)
            }
           progressBar6.visibility = View.GONE
        }
    }



   private fun check(reviews: Reviews):MutableList<ReviewModel>{
        val list= mutableListOf<ReviewModel>()
        state {
            reviewsViewModel.who.collect{
                when(it){
                    "my" -> list.addAll(reviews.my_reviews!!)
                    "shop"->list.addAll(reviews.reviews_for_user!!)
                }
            }
        }

        return list
    }
}
