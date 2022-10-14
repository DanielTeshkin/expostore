package com.expostore.ui.fragment.tender.item

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.databinding.TenderItemBinding
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.tender.TenderModel

import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.base.ImageItemAdapter
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.controllers.ControllerUI
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.product.utils.CharacteristicsData
import com.expostore.ui.fragment.product.utils.openBottomSheet
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.OnClickImage
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
    private val imageAdapter: ImageItemAdapter by lazy { ImageItemAdapter() }
    private val viewModel:TenderItemViewModel by viewModels()
    private val onClickImage : OnClickImage by lazy {
        object : OnClickImage {
            override fun click(bitmap: Bitmap) {
                ControllerUI(requireContext()).openImageFragment(bitmap)
                    .show(requireActivity().supportFragmentManager, "DialogImage") } } }
    override var isBottomNavViewVisible: Boolean=false
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
        TenderItemFragmentArgs.fromBundle(requireArguments()).apply { tender?.let {
            init(it)
        viewModel.saveTender(it)
        } }
        binding.btnBack.click { viewModel.navigateToBack() }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onResume()
    }
    fun init(result: TenderModel){
        binding.apply {
            imageAdapter.items = result.images?.map { it.file } ?: listOf()
            rvTenderImages.adapter=imageAdapter
            tvTenderName.text = result.title
            tvTenderPrice.text=result.price+ " " + "руб"
            imageAdapter.openImageOnFullScren={onClickImage.click(it)}
            tvTenderDescription.text=result.description
            ivProductShopImage.loadAvatar(result.shopModel.image.file)
            tvTenderCount.text=result.count.toString()
           llTenderCharacter.click { result.characteristicModel?.let {viewModel.navigateToCharacteristics(
                CharacteristicsData(it)
            ) } }
            if(result.communicationType == "chatting")btnCallDown.isVisible=false
            tvTenderLocation.text=result.location
            llProductShop.click { viewModel.navigateToShop() }
            btnCallDown.click { navigateToCall(result.author.username) }
            write.click { viewModel.createChat(result.id) }
            if(result.elected.notes.isEmpty()) editNote.text="Cоздать заметку"
            else tvProductNote.text=result.elected.notes
            editNote.click {viewModel.createIntentNote(result)  }

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