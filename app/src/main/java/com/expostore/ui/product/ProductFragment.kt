package com.expostore.ui.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: ProductFragmentBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var googleMap: GoogleMap

    var id: String? = null
    var shopId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        binding.productVM = productViewModel
        id = arguments?.getString("id")
        id?.let { productViewModel.id = it }
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)

//        binding.mapView.setOnTouchListener { _, motionEvent ->
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> binding.scrollView.requestDisallowInterceptTouchEvent(true)
//                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> binding.scrollView.requestDisallowInterceptTouchEvent(false)
//            }
//            binding.mapView.onTouchEvent(motionEvent)
//        }


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



    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.setAllGesturesEnabled(false)
    }

    fun setupInfo(info: ProductResponseData){
        binding.tvProductName.text = info.name
        binding.tvProductPrice.text = info.price + " руб."
        binding.tvProductAvailable.text = info.status
        binding.tvProductDescription.text = info.longDescription


        if (info.shop != null) {
            shopId = info.shop.id
            shopId?.let{ productViewModel.shopId = it}

            binding.tvProductShopName.text = info.shop.name
            binding.tvProductLocation.text = info.shop.address

            if (info.shop.lat != null && info.shop.long != null) {
                val marker = LatLng(info.shop.lat.toDouble(), info.shop.long.toDouble())
                googleMap.addMarker(
                    MarkerOptions()
                        .position(marker)
                        .title(info.shop.name)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15F))
            }
        }

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