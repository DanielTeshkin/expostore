package com.expostore.ui.fragment.product.myproducts.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.expostore.api.response.ProductResponse
import com.expostore.databinding.EditMyProductFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.InfoProfileModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditMyProductFragment : BaseFragment<EditMyProductFragmentBinding>(EditMyProductFragmentBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: EditMyProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("load") { _, bundle ->
            val result = bundle.getParcelable<ProductModel>("product")
            Log.i("mod",result?.id?:"d")
            if(result!=null) {
                 viewModel.saveProductInformation(result)
            }
        }
        subscribeOnChange()
         binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)


    }

    override fun onStart() {
        super.onStart()
        state {
            viewModel.product.collect {
                init(it)
                clickInstall(it)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onResume()
    }


    private fun subscribeOnChange() {
        val navigate: Show<ProductResponse> = { navigate(it) }
        viewModel.apply {
            subscribe(taleOff) { handleState(it, navigate) }
            subscribe(navigation) { navigateSafety(it) }
        }
    }

    private fun init(model: ProductModel) {
        val list = model.images.map { image -> image.file }
        val adapter = ImageAdapter()
        adapter.items = list
        binding.apply {
            tvProductName.text = model.name
            tvProductPrice.text = model.price
            tvProductDescription.text = model.longDescription
            rvProductImages.adapter = adapter
            tvProductLocation.text = model.shop.address
            tvProductRating.text = "Оценка: " + model.rating
            rbProductRating.rating = model.rating.toFloat()
        }
    }

    private fun clickInstall(model: ProductModel) {
        binding.apply {
            delete.click { viewModel.takeOffProduct(model.id) }
            edit.click {
                setFragmentResult("requestKey", bundleOf("product" to model))
                viewModel.navigateToAddProduct()
            }
        }
    }

    fun navigate(model: ProductResponse) {
        viewModel.navigateToProduct()
    }

    override fun onMapReady(map: GoogleMap) {
        state {
            viewModel.product.collect {
                val myLocation = LatLng(it.shop.lat, it.shop.lng)
                map.addMarker(MarkerOptions().position(myLocation))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,5f))
                map.uiSettings.isZoomGesturesEnabled = true
                map.uiSettings.isScrollGesturesEnabled=true
            }
        }


    }
}