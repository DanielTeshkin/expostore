package com.expostore.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.expostore.R

open class Dots {

    fun addDot(context: Context, count: Int, left:Int, top: Int, right: Int, bottom: Int, name: LinearLayout, inactiveDrawable: Int) {

        name.removeAllViews()

        val indicators = arrayOfNulls<ImageView>(count)

        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(left, top, right, bottom)
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i].apply {
                this?.setImageDrawable(ContextCompat.getDrawable(context, inactiveDrawable))
                this?.layoutParams = layoutParams
            }
            name.addView(indicators[i])
        }
    }

    fun currentDot(context: Context,index: Int, name: LinearLayout, activeDrawable: Int, inactiveDrawable: Int) {
        val childCount = name.childCount
        for (i in 0 until childCount){
            val imageView = name[i] as ImageView
            if(i == index) imageView.setImageDrawable(ContextCompat.getDrawable(context, activeDrawable))
            else imageView.setImageDrawable(ContextCompat.getDrawable(context, inactiveDrawable))
        }
    }
}