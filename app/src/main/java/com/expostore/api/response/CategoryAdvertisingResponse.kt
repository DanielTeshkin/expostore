package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class CategoryAdvertisingResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("date_created")
	val dateCreated: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
