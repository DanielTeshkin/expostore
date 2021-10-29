package com.expostore.ui.product.reviews

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.api.pojo.getreviews.Review
import com.expostore.api.pojo.getreviews.ReviewsResponseData
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.databinding.ReviewsFragmentBinding
import com.expostore.ui.product.ProductViewModel
import com.expostore.utils.DetailCategoryRecyclerViewAdapter
import com.expostore.utils.ReviewRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsFragment : Fragment() {

    private lateinit var binding: ReviewsFragmentBinding
    private lateinit var reviewsViewModel: ReviewsViewModel
    lateinit var mAdapter: ReviewRecyclerViewAdapter
    val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
    var id: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.reviews_fragment, container, false)
        reviewsViewModel = ViewModelProvider(this).get(ReviewsViewModel::class.java)
        binding.reviewsVM = reviewsViewModel
        id = arguments?.getString("id")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id?.let {
            serverApi.getReviews(it).enqueue(object : Callback<ReviewsResponseData> {
                override fun onResponse(call: Call<ReviewsResponseData>, response: Response<ReviewsResponseData>) {
                    if (response.isSuccessful) {
                        if(response.body() != null) {
                            if (response.body()!!.results != null) {
                                mAdapter = ReviewRecyclerViewAdapter(response.body()!!.results!!)
                                binding.rvReviews.apply {
                                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                                    adapter = mAdapter
                                }
                                mAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<ReviewsResponseData>, t: Throwable) {}
            })
        }
    }
}