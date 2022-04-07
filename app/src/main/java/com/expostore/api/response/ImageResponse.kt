package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(

	@field:SerializedName("file")
	val file: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)