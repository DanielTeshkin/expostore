package com.expostore.ui.fragment.map

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.databinding.MapChoiceFragmentBinding
import com.expostore.ui.base.BaseLocationFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.comparison_item.*


class MapChoiceFragment :
    BaseLocationFragment<MapChoiceFragmentBinding>(MapChoiceFragmentBinding::inflate)
    ,OnMapReadyCallback{
    private lateinit var googleMap: GoogleMap
    private var markerPosition: Marker? = null
    private var circle:Circle?=null
    private var circleOptions:CircleOptions?=null
    private val viewModel :MapChoiceViewModel by viewModels()
    override var isBottomNavViewVisible: Boolean=false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mapChoice.onCreate(savedInstanceState)
            mapChoice.getMapAsync(this@MapChoiceFragment)
        }
    }


    override fun onStart() {
        super.onStart()
        binding.mapChoice.onStart()
        binding.btnChoice.click { viewModel.navigate() }

    }
    override fun onResume() {
        super.onResume()
        binding.mapChoice.onResume()

    }

    override fun onPause() {
        super.onPause()
        binding.mapChoice.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapChoice.onStop()
    }
         fun drawCircle(location: LatLng){
             circle?.remove()
            circle= googleMap.addCircle(
                 CircleOptions()
                     .center(location)
                     .radius(1000.0)
                     .strokeWidth(2f)
                     .strokeColor(Color.BLUE)
                     .fillColor(Color.parseColor("#500084d3"))
             )
         }


    private fun myLocation() {
        myLocation?.let { location ->
            if (markerPosition != null) {
                moveToLocation(location)
                return@let
            }
            val latLng = LatLng(location.latitude, location.longitude)
            viewModel.saveLocation(latLng)
            val markerOptions =
                MarkerOptions()
                    .position(LatLng(location.latitude,location.longitude))
                    .draggable(true)


            moveToLocation(location)
            googleMap.addMarker(markerOptions)?.let { markerPosition = it }
            drawCircle(LatLng(location.latitude,location.longitude))
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


    override fun onLocationChange(location: Location) {
        markerPosition?.position = LatLng(location.latitude, location.longitude)
        myLocation()
    }

    override fun onLocationFind(location: Location): Boolean {
        Log.i("loc", location.latitude.toString())
        if (!::googleMap.isInitialized) {
            return false
        }
        myLocation()
        return true
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.setOnMarkerDragListener(object :GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {
                drawCircle(p0.position)
                viewModel.saveLocation(p0.position)
            }

            override fun onMarkerDragEnd(p0: Marker) {
                if(markerPosition!=null) markerPosition?.remove()
                val markerOptions = MarkerOptions().position(LatLng(p0.position.latitude,p0.position.longitude))
                googleMap.addMarker(markerOptions)?.let { markerPosition = it }
                viewModel.saveLocation(LatLng(p0.position.latitude,p0.position.longitude))
                drawCircle(p0.position)
            }

            override fun onMarkerDragStart(p0: Marker) {
                drawCircle(p0.position)
                viewModel.saveLocation(p0.position)
            }

        })
        googleMap.setOnMapClickListener { location ->
            if (markerPosition !=null)markerPosition?.remove()

            Log.i("latitude",location.latitude.toString())
                val markerOptions = MarkerOptions().position(LatLng(location.latitude,location.longitude)).draggable(true)
               viewModel.saveLocation(location)
                googleMap.addMarker(markerOptions)?.let { markerPosition = it }
            drawCircle(location)

        }
        viewModel.apply {
            //subscribe(location){drawCircle()}
          //  subscribe(distance){drawCircle()}
            subscribe(navigation){navigateSafety(it)}
        }

    }


    private fun addCameraTargetToPath() {

        circleOptions= CircleOptions()
        .center(googleMap.cameraPosition.target)
            .radius(googleMap.cameraPosition.zoom.toDouble())
            .strokeColor(Color.RED)
            .fillColor(Color.BLUE)

    }

}