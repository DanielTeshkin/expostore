package com.expostore.ui.fragment.search.filter.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultModel (
    val city:String,
    val flag:String):Parcelable
