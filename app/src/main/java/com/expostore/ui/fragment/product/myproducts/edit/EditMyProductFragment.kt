package com.expostore.ui.fragment.product.myproducts.edit

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.databinding.EditMyProductFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.base.ImageAdapter

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result=EditMyProductFragmentArgs.fromBundle(requireArguments()).product
        viewModel.saveProductInformation(result)
        subscribeOnChange()
         binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)


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
            start(findNavController())
            subscribe(product){
                init(it)
                clickInstall(it)
            }
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
            btnBack.click { viewModel.navigateToBack() }
        }
    }

    private fun clickInstall(model: ProductModel) {
        binding.apply {
            state { viewModel.textButton.collect { delete.text=it } }
            state { viewModel.buttonVisible.collect { delete.isVisible=it} }
            delete.click { viewModel.changeStatusPublished() }
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