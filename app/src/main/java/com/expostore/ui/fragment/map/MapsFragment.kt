package com.expostore.ui.fragment.map

import android.graphics.Point
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.R
import com.expostore.databinding.FragmentMapsBinding
import com.expostore.ui.base.BaseLocationFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : BaseLocationFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate),
    OnMapReadyCallback {

    private lateinit var map: GoogleMap


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onLocationChange(location: Location) {
        TODO("Not yet implemented")
    }

    override fun onLocationFind(location: Location): Boolean {
        if (!::map.isInitialized) {
            return false
        }
       // myLocation()
        return true
    }

    override fun noPermission() {
        TODO("Not yet implemented")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // we need to initialise map before creating a circle
        with(map) {


            setOnMapLongClickListener { point ->
                // We know the center, let's place the outline at a point 3/4 along the view.

                val radiusLatLng = map.projection.fromScreenLocation(
                    Point(binding.root.height * 3 / 4, binding.root.width * 3 / 4)
                )
                // Create the circle.
                //val newCircle = DraggableCircle(point, point.distanceFrom(radiusLatLng))
                //circles.add(newCircle)
            }

            setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDragStart(marker: Marker) {
                    onMarkerMoved(marker)
                }

                override fun onMarkerDragEnd(marker: Marker) {
                    onMarkerMoved(marker)
                }

                override fun onMarkerDrag(marker: Marker) {
                    onMarkerMoved(marker)
                }
            })

            // Flip the red, green and blue components of the circle's stroke color.
            setOnCircleClickListener { c -> c.strokeColor = c.strokeColor xor 0x00ffffff }
        }

    }

    fun onMarkerMoved(marker: Marker): Boolean {
        when (marker) {
            //   centerMarker -> {
            //   circle.center = marker.position
            //   radiusMarker?.position = marker.position.getPointAtDistance(radiusMeters)
            //  }
            // radiusMarker -> {
            //    radiusMeters = centerMarker?.position?.distanceFrom(radiusMarker.position)!!
            //     circle.radius = radiusMeters
            // }
            // else -> return false
            //}
            // return true
        }
        return false
    }

    fun LatLng.distanceFrom(other: LatLng): Double {
        val result = FloatArray(1)
        Location.distanceBetween(latitude, longitude, other.latitude, other.longitude, result)
        return result[0].toDouble()
    }

    fun LatLng.getPointAtDistance(distance: Double): LatLng {
        val radiusOfEarth = 6371009.0
        val radiusAngle = (Math.toDegrees(distance / radiusOfEarth)
                / Math.cos(Math.toRadians(latitude)))
        return LatLng(latitude, longitude + radiusAngle)
    }
}


