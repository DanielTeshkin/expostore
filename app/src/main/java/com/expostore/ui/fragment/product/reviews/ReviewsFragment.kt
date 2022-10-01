package com.expostore.ui.fragment.product.reviews

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ReviewsFragmentBinding
import com.expostore.model.review.ReviewModel
import com.expostore.model.review.Reviews
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.ui.controllers.ControllerUI
import com.expostore.ui.state.ResponseState
import com.expostore.utils.OnClickImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class ReviewsFragment : BaseFragment<ReviewsFragmentBinding>(ReviewsFragmentBinding::inflate) {

    private val reviewsViewModel: ReviewsViewModel by viewModels()
    private val list= mutableListOf<ReviewModel>()
    private val onClickImage:OnClickImage by lazy {
        object :OnClickImage{
            override fun click(bitmap: Bitmap) {
                ControllerUI(requireContext()).openImageFragment(bitmap)
                    .show(requireActivity().supportFragmentManager, "DialogImage")
            }

        }
    }
    private val reviewsAdapter:ReviewRecyclerViewAdapter by lazy {
        ReviewRecyclerViewAdapter(list,onClickImage)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvReviews.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter =reviewsAdapter
            }

        }
        when(val flag=ReviewsFragmentArgs.fromBundle(requireArguments()).flag){
            "list"-> list.addAll(ReviewsFragmentArgs.fromBundle(requireArguments()).reviews!!.reviews)
            else->{
                reviewsViewModel.whoUpdate(flag)
                reviewsViewModel.load()
                subscribeViewModel()
            }

        }
    }

  private fun subscribeViewModel(){
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
        binding.progressBar6.visibility = View.GONE
         check(reviews)
    }



   private fun check(reviews: Reviews){

       state {
            reviewsViewModel.who.collect{
                when(it){
                    "my" -> list.addAll(reviews.my_reviews!!)
                    "shop"->list.addAll(reviews.reviews_for_user!!)
                }
                reviewsAdapter.notifyDataSetChanged()
                binding.progressBar6.visibility = View.GONE
            }
        }
   }
}
