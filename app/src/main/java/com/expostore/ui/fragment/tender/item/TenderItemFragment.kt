package com.expostore.ui.fragment.tender.item

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.expostore.databinding.TenderItemBinding
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
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
        val goToChat:Show<MainChat> ={ openChat(it)}
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(chatUI){handleState(it,goToChat)}
        }
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }
    override fun onStart() {
        super.onStart()
        val result=TenderItemFragmentArgs.fromBundle(requireArguments()).tender
            binding.apply {
                if (result!=null) {
                    imageAdapter.items = result.images?.map { it.file } ?: listOf()
                    rvTenderImages.adapter=imageAdapter
                    tvTenderName.text = result.title
                    tvTenderPrice.text=result.price+ " " + "руб"
                    tvTenderDescription.text=result.description
                    tvTenderCount.text=result.count.toString()
                    tvTenderCharcter.click {
                        result.characteristicModel?.let { openBottomSheet(it,requireContext()) }
                    }
                    tvTenderLocation.text=result.location
                    llProductShop.click {
                     viewModel.navigateToShop()
                    }
                    btnCallDown.click {
                        navigateToCall(result.author.username)
                    }
                    write.click {
                        viewModel.createChat(result.id)
                    }
                    viewModel.saveTender(result)


                }

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
    private fun openChat(chat:MainChat){
        Log.i("ddd","ddd")
    }


}