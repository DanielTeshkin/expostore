package com.expostore.ui.fragment.search.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.R
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.extension.toMarker
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseLocationFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click

import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.main.adapter.ProductMarkerApi
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import com.expostore.ui.fragment.specifications.DataModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseLocationFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate),ProductMarkerApi,OnMarkerClickListener {

    override val viewModel: SearchViewModel by viewModels()
    private val myAdapter: ProductsAdapter by lazy { ProductsAdapter(requireContext()) }
    private var markerPosition: Marker? = null
    private val markers = mutableMapOf<Marker, ProductModel>()
    private val showMarkersProduct: Show<List<ProductModel>> by lazy { { installMarkerProducts(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        startSearch()
        installSubscribes()
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

    override fun onStart() {
        super.onStart()
        binding.searchMapView.onStart()
        state { viewModel.getBaseProducts()?.collect {
            installMarkerProducts(it)
        } }
        val result = SearchFragmentArgs.fromBundle(requireArguments()).filter
        if (result != null) searchWithFilters(result)
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

    private fun initUI() {
        initAdapter()
        buttonInstall()
        popupMenuLoad()
    }

    private fun installSubscribes() {
        viewModel.apply {
            getBaseProducts()
            subscribe(productsMarkerUI) {
                handleState(it, showMarkersProduct)
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
        myAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh == LoadState.Loading)
                binding.progressBar8.visibility = View.VISIBLE
            else binding.progressBar8.visibility = View.GONE
        }
    }

    private fun buttonInstall() {
        binding.apply { filter.click { viewModel.apply {
                    val result = DataModel(city = city.value, flag = "product")
                    setFragmentResult("new_key", bundleOf("info" to result))
                    navigateToFilter()
                }
            }

        }
    }

    private fun popupMenuLoad() {
        val popupMenu = PopupMenu(requireContext(), binding.sort)
        popupMenu.inflate(R.menu.sort_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.newList -> {
                    val sort = "date_created"
                    binding.sort.text = "По новизне"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.ratingList -> {
                    val sort = "avg"
                    binding.sort.text = "По рейтингу"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.priceList -> {
                    val sort = "price"
                    binding.sort.text = "По цене"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.popularList -> {
                    val sort = "count_views"
                    binding.sort.text = "По популярности"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.publicList -> {
                    val sort = "end_date_of_publication"
                    binding.sort.text = "По дате публикации"
                    searchWithFilters(FilterModel(sort = sort))
                }

            }
            false
        }
        binding.sort.click {
            popupMenu.show()
        }
    }

    private fun openProductScreen(model: ProductModel) {
        viewModel.navigateToProduct(model)
    }

    private fun startSearch() {
        lifecycleScope.launch {
            viewModel.search?.collectLatest { model ->
                    myAdapter.submitData(model) } } }

    private fun searchWithFilters(result: FilterModel) {
        viewModel.apply {
            lifecycleScope.launch {
                searchWithFilters(result)?.collectLatest {
                        myAdapter.submitData(it)
                    }
            } } }

    private fun installMarkerProducts(list: List<ProductModel>) {
        list.map {
            addProductMarker(it)
        }
    }

    private fun addProductMarker(it: ProductModel) {

        Glide.with(requireContext()).asBitmap().load(it.images[0].file).centerCrop().circleCrop().into( object :
            CustomTarget<Bitmap>( 70,  70){

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val latLng = LatLng(it.shop.lat, it.shop.lng)

                val marker=  googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)

                        .icon(
                            BitmapDescriptorFactory.fromBitmap(resource))
                        .anchor(1f,0.5f).flat(true)
                )!!
                markers[marker] = it
                googleMap.setOnMarkerClickListener { marker->
                    markers[marker]?.let { openProductScreen(it) }
                    false
                }

            }
            override fun onLoadCleared(placeholder: Drawable?) {
                TODO("Not yet implemented")
            }
        })

    }
    override fun onLocationChange(location: Location) {
            markerPosition?.position = LatLng(location.latitude, location.longitude)
            myLocation()
        }

    override fun myLocation() {
            myLocation?.let { location ->
                if (markerPosition != null) {
                    moveToLocation(location)
                    return@let
                }
                val latLng = LatLng(location.latitude, location.longitude)
                viewModel.apply {
                    //  fetchLocationProduct(lat = location.latitude, long = location.latitude, name = null)
                    saveLocation(location.latitude, location.longitude)
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

    override fun addMarker(it: ProductModel) {}
    override fun onMarkerClick(p0: Marker): Boolean {
        markers[p0]?.let { openProductScreen(it) }
        return true
    }
    override fun loadSelections(list: List<SelectionModel>) {
        val events= getClickListener(list)
        myAdapter.apply {
            onCallItemClickListener = { events.onClickCall(it) }
            onItemClickListener = { events.onClickProduct(it) }
            onLikeItemClickListener = { events.onClickLike(it) }
            onMessageItemClickListener = { events.onClickMessage(it) }
            onAnotherItemClickListener ={events.onClickAnother(it)} }
    }
}


