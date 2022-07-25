package com.expostore.ui.fragment.specifications

import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataModel(
    val name:String="",
    val priceMin:String?="",
    val priceMax:String?="",
    val count:String?=null,
    val location:String?=null,
    val city:String?=null,
    val description:String?=null,
    val flag:String=""
    ):Parcelable


