package com.expostore.ui.fragment.product

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.ui.base.BaseFragment
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


class ProductFragment : BaseFragment<ProductFragmentBinding>(ProductFragmentBinding::inflate),
    OnMapReadyCallback {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var googleMap: GoogleMap

    var id: String? = null
    var shopId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        id = arguments?.getString("id")
        id?.let { productViewModel.id = it }
        productViewModel.context = requireContext()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProductAllReviews.setOnClickListener {
            productViewModel.navigateToReviews(it)
        }

        binding.btnProductAddReview.setOnClickListener {
            productViewModel.navigateToAddReview(it)
        }

        binding.btnQrCode.setOnClickListener {
            productViewModel.navigateToQrCode(it)
        }

        binding.llProductShop.setOnClickListener {
            productViewModel.navigateToReviews(it)
        }

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


        val token = AppPreferences.getSharedPreferences(requireContext()).getString("token", "")
       /* val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        id?.let {
            serverApi.getProduct("Bearer $token", it)
                .enqueue(object : Callback<ProductResponseData> {
                    override fun onResponse(
                        call: Call<ProductResponseData>,
                        response: Response<ProductResponseData>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body() != null)
                                setupInfo(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<ProductResponseData>, t: Throwable) {}
                })
        }*/



        binding.btnCall.setOnClickListener {
            callNumber()
        }

        binding.btnCallDown.setOnClickListener {
            callNumber()
        }

    }


    private fun callNumber() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestMultiplePermissions.launch(arrayOf(android.Manifest.permission.CALL_PHONE))
            return
        } else {
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:777777777")
            startActivity(intent)
        }
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { _ ->
            callNumber()
        }
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.setAllGesturesEnabled(false)
    }

    fun setupInfo(info: ProductResponseData) {
        binding.tvProductName.text = info.name
        binding.tvProductPrice.text = info.price + " руб."
        binding.tvProductAvailable.text = info.status
        binding.tvProductDescription.text = info.longDescription

        //productViewModel.phoneSeller = info.owner.

        binding.mapView.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:37.7749,-122.4194")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        if (info.shop != null) {
            shopId = info.shop.id
            shopId?.let { productViewModel.shopId = it }

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

        if (info.images == null) info.images = arrayListOf(
            CategoryProductImage(null, null),
            CategoryProductImage(null, null),
            CategoryProductImage(null, null)
        )

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


        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:$77777777")
            startActivity(intent)
        }
    }
}