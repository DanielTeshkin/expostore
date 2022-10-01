package com.expostore.ui.fragment.tender.my.edit

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.expostore.databinding.EditMyTenderFragmentBinding
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.fragments.BaseFragment

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
class EditMyTenderFragment : BaseFragment<EditMyTenderFragmentBinding>(EditMyTenderFragmentBinding::inflate),OnMapReadyCallback {

    private val editMyTenderViewModel: EditMyTenderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result=EditMyTenderFragmentArgs.fromBundle(requireArguments()).tender
        editMyTenderViewModel.saveInfoTender(result)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        editMyTenderViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }

    }

    override fun onStart() {
        super.onStart()
        state {
            editMyTenderViewModel.tender.collect {
               init(it)
                clickInstall(it)
            }
        }
    }

   private fun init(model: TenderModel){
       binding.apply {
           tvProductName.text = model.title
           tvProductPrice.text = model.price
           tvProductDescription.text=model.description
           rvProductImages.apply {
               val myAdapter=ImageAdapter()
              myAdapter.items= model.images!!.map { it.file }
               adapter=myAdapter
           }
           tvProductLocation.text=model.location


       }
   }
    private fun clickInstall(model: TenderModel){
        binding.apply {
            state { editMyTenderViewModel.textButton.collect { delete.text=it } }
            state { editMyTenderViewModel.buttonVisible.collect { delete.isVisible=it} }
            delete.click { editMyTenderViewModel.changeStatusPublished() }
            edit.click {
                editMyTenderViewModel.navigateToCreateTender(model)
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        state {
            editMyTenderViewModel.tender.collect {
                val myLocation = LatLng(it.shopModel.lat, it.shopModel.lng)
                map.addMarker(MarkerOptions().position(myLocation))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,5f))
                map.uiSettings.isZoomGesturesEnabled = true
                map.uiSettings.isScrollGesturesEnabled=true
            }
        }
    }
}