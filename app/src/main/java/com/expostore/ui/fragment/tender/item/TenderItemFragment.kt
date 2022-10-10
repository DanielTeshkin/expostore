package com.expostore.ui.fragment.tender.item

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.databinding.TenderItemBinding
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.tender.TenderModel

import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.product.utils.openBottomSheet
import com.expostore.ui.fragment.profile.profile_edit.click
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TenderItemFragment:BaseFragment<TenderItemBinding>(TenderItemBinding::inflate),
    OnMapReadyCallback {
    private val imageAdapter: ImageAdapter by lazy { ImageAdapter() }
    private val viewModel:TenderItemViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            start(findNavController())
            subscribe(navigation){navigateSafety(it)}

        }
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }
    override fun onStart() {
        super.onStart()
        TenderItemFragmentArgs.fromBundle(requireArguments()).apply { tender?.let { init(it) } }
        binding.btnBack.click { viewModel.navigateToBack() }
    }
    fun init(result: TenderModel){
        binding.apply {
            imageAdapter.items = result.images?.map { it.file } ?: listOf()
            rvTenderImages.adapter=imageAdapter
            tvTenderName.text = result.title
            tvTenderPrice.text=result.price+ " " + "руб"
            tvTenderDescription.text=result.description
            ivProductShopImage.loadAvatar(result.shopModel.image.file)
            tvTenderCount.text=result.count.toString()
            tvTenderCharcter.click { result.characteristicModel?.let { openBottomSheet(it,requireContext()) } }
            tvTenderLocation.text=result.location
            llProductShop.click { viewModel.navigateToShop() }
            btnCallDown.click { navigateToCall(result.author.username) }
            write.click { viewModel.createChat(result.id) }
            if(result.elected.notes.isEmpty()) editNote.text="Cоздать заметку"
            else tvProductNote.text=result.elected.notes
            editNote.click {viewModel.navigateToNote(result)  }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        state {
            viewModel.tender.collect {
                val myLocation = LatLng(it.lat?:0.0, it.long?:0.0)
                map.addMarker(MarkerOptions().position(myLocation))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,5f))
                map.uiSettings.isZoomGesturesEnabled = true
                map.uiSettings.isScrollGesturesEnabled=true
            }
        }
    }


}