package com.expostore.ui.base.search.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.expostore.R
import com.expostore.extension.toMarker
import com.expostore.ui.base.fragments.BaseItemFragment
import com.expostore.ui.base.fragments.Inflate
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*


abstract class BaseLocationFragment<Binding : ViewBinding,T:Any,E,A>(private val inflate: Inflate<Binding>) :
    BaseItemFragment<Binding, T, E, A>(inflate),OnMapReadyCallback {

    var myLocation: Location? = null
    protected var markerPosition: Marker? = null
    private var isLocationFind = false

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var googleMap: GoogleMap

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            fetchLocation()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchLocation()

    }

    open fun onLocationChange(location: Location){
        markerPosition?.position = LatLng(location.latitude, location.longitude)
        myLocation()
    }
    open fun myLocation(){
        myLocation?.let { location ->
            if (markerPosition != null) {
                moveToLocation(location)
                return@let
            }
            val latLng = LatLng(location.latitude, location.longitude)
            saveCurrentLocation(location.latitude, location.longitude)

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
    private fun onLocationFind(location: Location): Boolean{
        if (!::googleMap.isInitialized) {
            return false
        }
        myLocation()
        return true
    }
   abstract fun saveCurrentLocation(lat:Double,long:Double)


    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestMultiplePermissions.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
            return
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    this.myLocation = it
                    if (!isLocationFind) {
                        isLocationFind = onLocationFind(it)
                    } else {
                        onLocationChange(it)
                    }
                }
            }
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

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
    }
}