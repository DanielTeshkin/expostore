package com.expostore.ui.fragment.category.personal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdatePersonalSelectionDate(
    val id:String,
    val name:String
):Parcelable