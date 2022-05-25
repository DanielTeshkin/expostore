package com.expostore.ui.fragment.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.ui.base.BaseFragment


class ProductFragment : BaseFragment<ProductFragmentBinding>(ProductFragmentBinding::inflate)
    {

   // private lateinit var productViewModel: ProductViewModel
   // private lateinit var googleMap: GoogleMap

   // var id: String? = null
   // var shopId: String? = null



    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
        //binding.tvProductAllReviews.setOnClickListener {
       //     productViewModel.navigateToReviews(it)
       // }

        //binding.btnProductAddReview.setOnClickListener {
        //    productViewModel.navigateToAddReview(it)
        //}

       // binding.btnQrCode.setOnClickListener {
       //     productViewModel.navigateToQrCode(it)
        //}

        //binding.llProductShop.setOnClickListener {
       //     productViewModel.navigateToReviews(it)
      //  }

      //  binding.mapView.onCreate(savedInstanceState)
       // binding.mapView.onResume()
       // binding.mapView.getMapAsync(this)

//        binding.mapView.setOnTouchListener { _, motionEvent ->
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> binding.scrollView.requestDisallowInterceptTouchEvent(true)
//                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> binding.scrollView.requestDisallowInterceptTouchEvent(false)
//            }
//            binding.mapView.onTouchEvent(motionEvent)
//        }


      //  val token = AppPreferences.getSharedPreferences(requireContext()).getString("token", "")
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



       // binding.btnCall.setOnClickListener {
       //     callNumber()
       // }

       // binding.btnCallDown.setOnClickListener {
       //     callNumber()
       // }

   //



  //  private fun callNumber() {

     //   if (ContextCompat.checkSelfPermission(
         //       requireContext(),
          //      android.Manifest.permission.CALL_PHONE
          //  ) != PackageManager.PERMISSION_GRANTED
       // ) {
       //     requestMultiplePermissions.launch(arrayOf(android.Manifest.permission.CALL_PHONE))
       //     return
      //  } else {
         //   val intent = Intent(Intent.ACTION_CALL);
        //    intent.data = Uri.parse("tel:777777777")
          //  startActivity(intent)
       // }
   // }

    //private val requestMultiplePermissions = registerForActivityResult(
     //   ActivityResultContracts.RequestMultiplePermissions()
  //  ) { permissions ->
     //   permissions.entries.forEach { _ ->
    //        callNumber()
      //  }
    //}


    //override fun onMapReady(map: GoogleMap) {
      // googleMap = map
        //googleMap.uiSettings.setAllGesturesEnabled(false)
   // }

   // fun setupInfo(info: ProductResponseData) {
     //   binding.tvProductName.text = info.name
     //   binding.tvProductPrice.text = info.price + " руб."
     //   binding.tvProductAvailable.text = info.status
   //   binding.tvProductDescription.text = info.longDescription

        //productViewModel.phoneSeller = info.owner.

     //   binding.mapView.setOnClickListener {
        //    val gmmIntentUri = Uri.parse("geo:37.7749,-122.4194")
         //   val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
          //  mapIntent.setPackage("com.google.android.apps.maps")
          //  startActivity(mapIntent)
       // }

       // if (info.shop != null) {
         //   shopId = info.shop.id
          //  shopId?.let { productViewModel.shopId = it }

          //  binding.tvProductShopName.text = info.shop.name
        //    binding.tvProductLocation.text = info.shop.address




}