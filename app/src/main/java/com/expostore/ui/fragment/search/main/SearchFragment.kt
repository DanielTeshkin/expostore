package com.expostore.ui.fragment.search.main

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.extension.toMarker
import com.expostore.ui.base.BaseLocationFragment
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.expostore.ui.state.SearchState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseLocationFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    private val adapter = ProductsAdapter()

    private var markerPosition: Marker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@SearchFragment)

            recyclerView.adapter = adapter

            location.setOnClickListener {
                this@SearchFragment.myLocation?.let {
                    myLocation()
                }
            }

            filter.setOnClickListener { navigateSafety(SearchFragmentDirections.actionSearchFragmentToSearchFilterFragment()) }
        }
        adapter.onItemClickListener = {
            val bundle = Bundle()
            bundle.putString("id", it.id)
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.action_searchFragment_to_productFragment, bundle)
        }

        adapter.onLikeItemClickListener = { viewModel.selectFavorite(it) }

        adapter.onCallItemClickListener = { navigateToCall(it) }

        viewModel.apply {
            start()
            subscribe(uiState) { handleState(it) }
        }
        collectUiState()
    }

    private fun handleState(state: SearchState) {
        when (state) {
            is SearchState.Loading -> handleLoading(state.isLoading)
            is SearchState.Error -> handleError(state.throwable)
            is SearchState.SelectFavorite -> handleFavorite(state.id, state.isSuccess)
        }
    }

    private fun handleError(throwable: Throwable) {
        // TODO: сделать отображение ошибки с сервера
        //временная реализация
        throwable.message?.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleLoading(isLoading: Boolean) {

    }

    private fun handleFavorite(id: String, isSuccess: Boolean) {
        adapter.processLike(id, isSuccess)
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchProduct().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.searchMapView.onStart()
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

}