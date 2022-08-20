package com.expostore.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * @author Fedotov Yakov
 */
abstract class BaseLocationFragment<Binding : ViewBinding>(private val inflate: Inflate<Binding>) :
    BaseFragment<Binding>(inflate) {

    var myLocation: Location? = null
        private set

    private var isLocationFind = false

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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




    abstract fun onLocationChange(location: Location)

    abstract fun onLocationFind(location: Location): Boolean

    abstract fun noPermission()

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
    protected fun bitmapDescriptorFromVector(context: Context, imageProduct:String): BitmapDescriptor {
        val image= ImageView(context)
         Glide.with(context).load(imageProduct).into(image)
        image.drawable.toBitmap()
        val vectorDrawable =  image.drawable
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}