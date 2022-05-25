package com.expostore.ui.fragment.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.ui.fragment.product.reviews.ReviewRecyclerViewAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.collect


class ProductFragment : BaseFragment<ProductFragmentBinding>(ProductFragmentBinding::inflate),OnMapReadyCallback
    {
        private val productViewModel:ProductViewModel by viewModels()
        private val imageAdapter:ImageAdapter by lazy { ImageAdapter() }




    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey"){_,bundle->
            val result=bundle.getParcelable<ProductModel>("product")
            result?.let { productViewModel.saveProduct(it) }
        }

        setFragmentResultListener("new_key"){_,bundle->
            val result=bundle.getParcelable<ProductModel>("product")
            result?.let { productViewModel.saveProduct(it) }
        }
        subscribeUI()

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

       private fun subscribeUI(){
           observeNavigation()
           state {
               productViewModel.product.collect {
                   observeUI(it)
               }
           }

        }

        private fun observeUI(model: ProductModel) {
            binding.apply {
                tvProductPrice.text=model.price
                tvProductName.text=model.name
                tvProductDescription.text=model.longDescription
                imageAdapter.items =model.images.map { it.file }
                rvProductImages.adapter=imageAdapter
                ivProductShopImage.loadAvatar(model.shop.image.file)
                tvProductRating.text = "Оценка: " + model.rating
                rbProductRating.rating = model.rating.toFloat()
                rvProductReviews.layoutManager=LinearLayoutManager(requireContext())
            }

        }


        private fun observeNavigation(){
            productViewModel.apply {
                subscribe(navigation){navigateSafety(it)}
            }
        }

        override fun onMapReady(map: GoogleMap) {

            state {
                productViewModel.product.collect {
                    val myLocation = LatLng(it.shop.lat, it.shop.lng)
                    map.addMarker(MarkerOptions().position(myLocation))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 5f))
                    map.uiSettings.isZoomGesturesEnabled = true
                    map.uiSettings.isScrollGesturesEnabled = true
                }
            }
        }


    }