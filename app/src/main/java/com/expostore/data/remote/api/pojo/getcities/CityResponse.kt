package com.expostore.data.remote.api.pojo.getcities

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
{
    override fun toString(): String {
        return name
    }
}



