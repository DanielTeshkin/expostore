package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class ShopResponse(

	@field:SerializedName("image")
	val image: ImageResponse? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("author")
	val author: AuthorResponse? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("long")
	val lng: Double? = null,

	@field:SerializedName("shopping_center")
	val shoppingCenter: String? = null,
	@field:SerializedName("floor_and_office_number")
    val floor_and_office_number:String?=null

)