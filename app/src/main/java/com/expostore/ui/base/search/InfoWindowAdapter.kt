package com.expostore.ui.base.search

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.databinding.InfoWindowBinding
import com.expostore.extension.load
import com.expostore.ui.fragment.profile.profile_edit.click
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class InfoWindowAdapter(private val image:String, private val context:Context ):GoogleMap.InfoWindowAdapter {
   var infoWindowClick:CustomInfoWindowClick?=null
    override fun getInfoContents(p0: Marker): View? {
       return null
    }

    override fun getInfoWindow(p0: Marker): View? {
        val binding:InfoWindowBinding= InfoWindowBinding.inflate(
            LayoutInflater.from(context),null,false
        )
        Glide.with(context)
            .asBitmap()
            .load(image)

            .into(object : CustomTarget<Bitmap>(100,100){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.info.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        return binding.root
    }


}
interface CustomInfoWindowClick{
    fun invoke(marker: Marker)
}