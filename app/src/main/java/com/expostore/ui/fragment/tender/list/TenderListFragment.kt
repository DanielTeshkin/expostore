package com.expostore.ui.fragment.tender.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.api.request.ChatCreateRequest

import com.expostore.databinding.TenderListFragmentBinding
import com.expostore.extension.toMarker
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.name
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.BaseLocationFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.filter.models.ResultModel

import com.expostore.ui.fragment.search.main.SearchViewModel
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.expostore.ui.fragment.tender.list.adapter.TenderAdapter
import com.expostore.ui.state.ResponseState

import com.expostore.ui.fragment.tender.utils.OnClickTender
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class TenderListFragment :
    BaseLocationFragment<TenderListFragmentBinding>(TenderListFragmentBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: TenderListViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var myAdapter: TenderAdapter
    private var markerPosition: Marker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter= TenderAdapter()

        myAdapter.onCallItemClickListener={navigateToCall(it)}
        myAdapter.onMessageItemClickListener={ model->
            viewModel.apply {
                state {
                    createChat(model).collect {

                        val result = InfoItemChat(
                            it.identify()[1],
                            it.identify()[0],
                            it.chatsId(),
                            it.imagesProduct(),
                            it.productsName(), it.identify()[3]
                        )
                        setFragmentResult("new_key", bundleOf("info" to result))
                        viewModel.navigateToChat()
                    }
                }
            }
        }
        myAdapter.onItemClickListener={
            viewModel.navigateToItem()
        }
        binding.rvTenderList.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
 binding.createTender.click { viewModel.createTender() }

        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    loadTenderList()
                        .collectLatest {
                            myAdapter.submitData(it)
                        }
                }
            }

        }


        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@TenderListFragment)


            location.setOnClickListener {
                this@TenderListFragment.myLocation?.let {
                    myLocation()
                }
            }

            filter.setOnClickListener {
                val result= ResultModel("Москва","tender")
                setFragmentResult("new_key", bundleOf("info" to result))
                viewModel.navigateToFilter()

            }


        }
    }


    private fun searchWithFilters(result: FilterModel) {
        viewModel.apply {
            lifecycleScope.launch {
                viewModel.loadTenderList(priceFrom = result.price_min.toString(), priceUp = result.price_max.toString(), title = result.name, city = result.city
                )
                    .collectLatest{
                        myAdapter.submitData(lifecycle, PagingData.empty())
                        myAdapter.submitData(it)
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.searchMapView.onStart()
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<FilterModel>("filters")
            if (result!=null) searchWithFilters(result)

        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.searchMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.searchMapView.onStop()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
    }

    override fun onLocationChange(location: Location) {
        markerPosition?.position = LatLng(location.latitude, location.longitude)
        myLocation()
    }

    override fun onLocationFind(location: Location): Boolean {
        if (!::googleMap.isInitialized) {
            return false
        }
        myLocation()
        return true
    }

    private fun myLocation() {
        myLocation?.let { location ->
            if (markerPosition != null) {
                moveToLocation(location)
                return@let
            }
            val latLng = LatLng(location.latitude, location.longitude)
            viewModel.apply {
                //  fetchLocationProduct(lat = location.latitude, long = location.latitude, name = null)
               // saveLocation(location.latitude,location.longitude)
            }

            val markerOptions = MarkerOptions().position(latLng)
                .icon(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_marker
                    )?.toMarker
                )
            moveToLocation(location)
            googleMap.addMarker(markerOptions)?.let { markerPosition = it }
        }
    }

    private fun moveToLocation(location: Location, isAnimate: Boolean = true) {
        val latLng = LatLng(location.latitude, location.longitude)
        if (isAnimate) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        }
    }

    override fun noPermission() {
        /// viewModel.fetchProduct()
    }


}