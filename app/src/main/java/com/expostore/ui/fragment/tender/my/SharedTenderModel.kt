package com.expostore.ui.fragment.tender.my

import android.os.Parcelable
import com.expostore.model.tender.TenderModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SharedTenderModel(
    val status:String):Parcelable

