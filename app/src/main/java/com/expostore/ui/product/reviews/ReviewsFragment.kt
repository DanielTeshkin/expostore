package com.expostore.ui.product.reviews

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getreviews.ReviewsResponseData
import com.expostore.databinding.ReviewsFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.utils.ReviewRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsFragment : BaseFragment<ReviewsFragmentBinding>(ReviewsFragmentBinding::inflate) {

    private lateinit var reviewsViewModel: ReviewsViewModel
    lateinit var mAdapter: ReviewRecyclerViewAdapter
    val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewsViewModel = ViewModelProvider(this).get(ReviewsViewModel::class.java)
        id = arguments?.getString("id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id?.let {
            serverApi.getReviews(it).enqueue(object : Callback<ReviewsResponseData> {
                override fun onResponse(
                    call: Call<ReviewsResponseData>,
                    response: Response<ReviewsResponseData>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.results != null) {
                                mAdapter = ReviewRecyclerViewAdapter(response.body()!!.results!!)
                                binding.rvReviews.apply {
                                    layoutManager = LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
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