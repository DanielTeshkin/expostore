package com.expostore.ui.fragment.tender.list

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R

import com.expostore.databinding.TenderListFragmentBinding
import com.expostore.extension.toMarker
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseLocationFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.filter.models.FilterModel

import com.expostore.ui.general.other.OnClickBottomSheetTenderFragment
import com.expostore.ui.general.other.showBottomSheetTender
import com.expostore.ui.fragment.specifications.DataModel
import com.expostore.ui.fragment.tender.list.adapter.TenderAdapter

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
            createChat(model)
        }
        myAdapter.onItemClickListener={
            viewModel.navigateToItem(it)
        }
        myAdapter.onLikeItemClickListener={
            viewModel.selectFavorite(it)
        }
        myAdapter.onAnotherItemClickListener={
            showBottomSheetTender(requireContext(),it,initPersonalSelectionCLick())
        }
        binding.rvTenderList.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
        myAdapter.addLoadStateListener { loadState->
            if (loadState.refresh== LoadState.Loading)
                binding.progressBar9.visibility=View.VISIBLE
            else binding.progressBar9.visibility=View.GONE
        }
 binding.createTender.click { viewModel.createTender() }
          val show:Show<MainChat> = { openChat(it)}
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(chat){handleState(it)}
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                   loadTenderList()
                        .collect {
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
            val popupMenu= PopupMenu(requireContext(),binding.newFilter)
            popupMenu.inflate(R.menu.sort_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.newList ->{
                        val sort="date_created"
                        binding.newFilter.text = "По новизне"
                        searchWithFilters(FilterModel(sort=sort))
                    }
                    R.id.ratingList ->{
                        val sort="avg"
                        binding.newFilter.text ="По рейтингу"
                        searchWithFilters(FilterModel(sort=sort))
                    }
                    R.id.priceList ->{
                        val sort="price"
                        binding.newFilter.text = "По цене"
                        searchWithFilters(FilterModel(sort=sort))
                    }
                    R.id.popularList->{
                        val sort="count_views"
                        binding.newFilter.text = "По популярности"
                        searchWithFilters(FilterModel(sort=sort))
                    }
                    R.id.publicList->{
                        val sort="end_date_of_publication"
                        binding.newFilter.text = "По дате публикации"
                        searchWithFilters(FilterModel(sort=sort))
                    }

                }
                false
            }
            binding.newFilter.click {
                popupMenu.show()
            }

            filter.setOnClickListener {
                val result= DataModel(city = "Москва", flag  ="tender")
                setFragmentResult("new_key", bundleOf("info" to result))
                viewModel.navigateToFilter()

            }


        }
    }
    private fun openChat(it:MainChat){

            val result = InfoItemChat(
                it.identify()[1],
                it.identify()[0],
                it.chatsId(),
                it.imagesProduct(),
                it.productsName(), it.identify()[3]

            )
            viewModel.navigateToChat(result)

    }


    private fun searchWithFilters(result: FilterModel) {
        viewModel.apply {
            lifecycleScope.launch {
                loadTenderListWithFilters(result)
                    .collect{

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



    fun createChat(id:String){
        viewModel.createChat(id)





            }


    private fun initPersonalSelectionCLick(): OnClickBottomSheetTenderFragment {
        return object : OnClickBottomSheetTenderFragment {

            override fun call(username: String) {
                navigateToCall(username)
            }

            override fun createNote(model: TenderModel) {
                viewModel.navigateToNote(model)
            }

            override fun chatCreate(id: String) {
                createChat(id)
            }



            override fun block() {
                Log.i("ddd","dsdsd")
            }

        }
    }


}