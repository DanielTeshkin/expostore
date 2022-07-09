package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(

	@SerializedName("file")
	val file: String="" ,

	@SerializedName("id")
	val id: String=""
)