package com.expostore.ui.fragment.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InfoProfileModel(
    val name:String?,
    val surname:String?,
    val city: String,
    val email:String

):Parcelable
