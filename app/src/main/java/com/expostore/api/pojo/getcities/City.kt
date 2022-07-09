package com.expostore.api.pojo.getcities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val mapCity:Pair<String,Int>
) : Parcelable

val CityResponse.toModel : City
get() = City(
    mapCity =Pair(name,id)
)

