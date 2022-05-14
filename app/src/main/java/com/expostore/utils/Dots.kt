package com.expostore.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.expostore.R

open class Dots(val size:Int,val activeDrawable:Drawable,val passiveDrawable:Int, val linearLayout: LinearLayout, val context: Context) {

    fun drawDots(){

        val image=ImageView(context)
        val b=image.setImageDrawable(activeDrawable)
        linearLayout.addView(image)
    }

}