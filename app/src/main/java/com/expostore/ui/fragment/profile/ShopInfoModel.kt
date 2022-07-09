package com.expostore.ui.fragment.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopInfoModel(
    val name:String="",
    val shopping_center:String="",
    val address:String="",
    val info:Boolean=false
):Parcelable



