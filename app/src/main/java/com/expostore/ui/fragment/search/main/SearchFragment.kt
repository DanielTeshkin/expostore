package com.expostore.ui.fragment.search.main

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.api.request.ChatCreateRequest
import com.expostore.api.request.ProductChat
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.extension.toMarker
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.product.name
import com.expostore.ui.base.BaseLocationFragment
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName

import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.filter.models.ResultModel
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.expostore.ui.fragment.search.other.OnClickBottomSheetFragment

import com.expostore.ui.fragment.search.other.showBottomSheet
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseLocationFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var myAdapter: ProductsAdapter
    private var markerPosition: Marker? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       myAdapter= ProductsAdapter(requireContext())
        myAdapter.onCallItemClickListener={navigateToCall(it)}
        myAdapter.onItemClickListener={
            val result=it
            setFragmentResult("new_key", bundleOf("product" to result))
            viewModel.navigateToProduct()
        }
        myAdapter.onLikeItemClickListener= {
            viewModel.selectFavorite(it)
        }

        myAdapter.onMessageItemClickListener={ model->
            createChat(model.id)
        }
        binding.filter.setOnClickListener {
            viewModel.apply {
                val result= ResultModel(city.value,"product")
                setFragmentResult("new_key", bundleOf("info" to result))
                navigateToFilter()
            }
        }





        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(requireContext())
           adapter = myAdapter
            }


           viewModel.apply {
               subscribe(navigation){navigateSafety(it)}
               startSearch()
           }

        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@SearchFragment)
            location.setOnClickListener {
            this@SearchFragment.myLocation?.let {
               myLocation()
            }
        }
        }

}

fun createChat(id:String){
    viewModel.apply {
        state {
            createChat(id,"product").collect {

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
 private fun initPersonalSelectionCLick():OnClickBottomSheetFragment{
       return object :OnClickBottomSheetFragment{
           override fun createSelection(product: String) {
               setFragmentResult("name", bundleOf("product" to product))
               viewModel.navigateToSelectionCreate()
           }

           override fun addToSelection(id: String, product: String) {
               Log.i("add","dddd")
              viewModel.addToSelection(id, product)
           }

           override fun call(username: String) {
               navigateToCall(username)
           }

           override fun createNote(id: String) {
            Log.i("ddd","dsdsd")
           }

           override fun chatCreate(id: String) {
              createChat(id)
           }

           override fun share() {
               TODO("Not yet implemented")
           }

           override fun block() {
               TODO("Not yet implemented")
           }

       }
 }



  private fun startSearch(){
      lifecycleScope.launch {
                viewModel.search
                    .collect{
                        myAdapter.submitData(it)
                    }
            }

    }

   private fun searchWithFilters(result: FilterModel) {
       viewModel.apply {
           lifecycleScope.launch {
               searchWithFilters(result)
                   .collect{
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

        state {
            viewModel.getSelections().collect {
                myAdapter.onAnotherItemClickListener = { model ->
                    showBottomSheet(requireContext(), model,it,initPersonalSelectionCLick())

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
       // binding.searchMapView.onResume()

    }

    override fun onPause() {
        super.onPause()
        //binding.searchMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
      //  binding.searchMapView.onStop()
    }

    override fun onMapReady(map: GoogleMap) {
        Log.i("marker","aaa")
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
    }

    override fun onLocationChange(location: Location) {
       markerPosition?.position = LatLng(location.latitude, location.longitude)
        myLocation()
    }

    override fun onLocationFind(location: Location): Boolean {
        Log.i("loc",location.latitude.toString())
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
                saveLocation(location.latitude,location.longitude)
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

