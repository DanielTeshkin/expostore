package com.expostore.api.pojo.addshop

import com.expostore.api.response.AuthorResponse
import com.expostore.api.response.ImageResponse
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class AddShopRequestData(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("owner")
    val owner: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("long")
    val lng: Double? = null,

    @field:SerializedName("shopping_center")
    val shoppingCenter: String? = null

    )
fun returnShopModel(name: String?,address: String?,shoppingCenter: String?) = AddShopRequestData(name = name, address = address, shoppingCenter = shoppingCenter,
owner ="98d3bc04-8a90-447b-b2f3-42d1c1ef8ded"
    )




