package com.expostore.api.pojo.getcities

import com.fasterxml.jackson.annotation.JsonProperty
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



