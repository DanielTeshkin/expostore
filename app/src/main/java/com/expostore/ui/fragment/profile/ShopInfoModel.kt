package com.expostore.ui.fragment.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopInfoModel(
    val name:String="",
    val shopping_center:String="",
    val address:String="",
    val floor_and_office_number:String="",
    val info:Boolean=false
):Parcelable
fun shopInfoCreate(name:String,
                   shopping_center: String,
                   address: String,
                   floor_and_office_number: String,
                   info: Boolean) = ShopInfoModel(name, shopping_center, address,floor_and_office_number, info)




