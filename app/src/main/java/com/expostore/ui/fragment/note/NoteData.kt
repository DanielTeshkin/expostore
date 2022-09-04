package com.expostore.ui.fragment.note

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteData(
    val id:String?="",
    val flag:String?="",
    val isLiked:Boolean?=false,
    val flagNavigation:String?="product"
):Parcelable