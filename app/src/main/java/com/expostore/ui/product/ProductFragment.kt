package com.expostore.ui.product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.utils.ProductImageRecyclerViewAdapter
import com.expostore.utils.ReviewRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : Fragment() {

    private lateinit var binding: ProductFragmentBinding
    private lateinit var productViewModel: ProductViewModel
    var id: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        binding.productVM = productViewModel
        id = arguments?.getString("id")
        id?.let { productViewModel.id = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = (context as MainActivity).sharedPreferences.getString("token", "")
        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        id?.let {
            serverApi.getProduct("Bearer $token", it).enqueue(object : Callback<ProductResponseData> {
            override fun onResponse(call: Call<ProductResponseData>, response: Response<ProductResponseData>) {
                if (response.isSuccessful) {
                    if(response.body() != null)
                        setupInfo(response.body()!!)
                }
            }
            override fun onFailure(call: Call<ProductResponseData>, t: Throwable) {}
        })
        }
    }

    fun setupInfo(info: ProductResponseData){
        binding.tvProductName.text = info.name
        binding.tvProductCategory.text = info.category
        binding.tvProductPrice.text = info.price
        binding.tvProductAvailable.text = info.status
        binding.tvProductDescription.text = info.longDescription

        binding.tvProductShopName.text = info.shop?.name
        binding.tvProductLocation.text = info.shop?.address

        if (info.rating != null) {
            binding.tvProductRating.text = "Оценка: " + info.rating
            binding.rbProductRating.rating = info.rating.toFloat()
        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvProductImages)

        if (info.images == null) info.images = arrayListOf(CategoryProductImage(null,null),CategoryProductImage(null,null),CategoryProductImage(null,null))

        binding.rvProductImages.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ProductImageRecyclerViewAdapter(context, info.images!!, null, null, null)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }

        info.reviews?.let {
            binding.rvProductReviews.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = ReviewRecyclerViewAdapter(info.reviews)
            }
        }
    }
}