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
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("shopping_center")
    val shoppingCenter: String? = null,
    @field:SerializedName("floor_and_office_number")
    val floor_and_office_number: String? = null,
    @field:SerializedName("phone")
val phone:String?=null
)
fun returnShopModel(name: String?,address: String?,shoppingCenter: String?,floor_and_office_number: String?,phone: String?) =
    AddShopRequestData(name = name, address = address, shoppingCenter = shoppingCenter, floor_and_office_number = floor_and_office_number, phone =phone

    )




