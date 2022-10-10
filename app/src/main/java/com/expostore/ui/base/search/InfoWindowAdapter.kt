package com.expostore.ui.base.search

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import com.expostore.databinding.InfoWindowBinding
import com.expostore.ui.fragment.chats.loadTabImage
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class InfoWindowAdapter(private val image:String,context:Context ):GoogleMap.InfoWindowAdapter {
    private val binding:InfoWindowBinding= InfoWindowBinding.inflate(
        LayoutInflater.from(context),null,false
    )
    override fun getInfoContents(p0: Marker): View? {
       binding.info.loadTabImage(image)
        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        binding.info.loadTabImage(image)
        return binding.root
    }

}