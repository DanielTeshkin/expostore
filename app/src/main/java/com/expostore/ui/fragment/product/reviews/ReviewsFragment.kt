package com.expostore.ui.fragment.product.reviews

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ReviewsFragmentBinding
import com.expostore.model.review.ReviewModel
import com.expostore.model.review.Reviews
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.general.ControllerUI
import com.expostore.ui.fragment.product.ReviewsModel
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
        installSetFragmentListeners()
        subscribeViewModel()
        binding.apply {
            rvReviews.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter =reviewsAdapter
            }
            progressBar6.visibility = View.GONE
        }
    }

  private fun subscribeViewModel(){
      reviewsViewModel.apply {
            subscribe(reviews){handleResult(it)}
            subscribe(navigation){navigateSafety(it)}

        }
    }
    private fun installSetFragmentListeners(){
        setFragmentResultListener("requestKey"){_, bundle->
            val result=bundle.getString("name")
            if(result!=null) {
                reviewsViewModel.whoUpdate(result)
                reviewsViewModel.load()
            }
        }
        setFragmentResultListener("reviews"){_, bundle->
            val result=bundle.getParcelable<ReviewsModel>("list")
            if(result!=null) list.addAll(result.reviews)
        }
    }


    fun handleResult(state:ResponseState<Reviews>){
        when(state) {
            is ResponseState.Success -> loadReviews(state.item)
            is ResponseState.Error -> Toast.makeText(requireContext(),state.throwable.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadReviews(reviews: Reviews) {
         check(reviews)
    }



   private fun check(reviews: Reviews){
       state {
            reviewsViewModel.who.collect{
                when(it){
                    "my" -> list.addAll(reviews.my_reviews!!)
                    "shop"->list.addAll(reviews.reviews_for_user!!)
                }
            }
        }
   }
}
