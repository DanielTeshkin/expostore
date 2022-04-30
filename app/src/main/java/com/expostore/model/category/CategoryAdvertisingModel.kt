package com.expostore.model.category

import com.expostore.api.response.CategoryAdvertisingResponse
import com.google.gson.annotations.SerializedName

data class CategoryAdvertisingModel(
	@SerializedName("image" )val image: String,
	@SerializedName("date_created" )val dateCreated: String,
	@SerializedName("id")val id: String,
	@SerializedName("url" )	val url: String
)

val CategoryAdvertisingResponse.toModel: CategoryAdvertisingModel
	get() = CategoryAdvertisingModel(
		image ?: "",
		dateCreated ?: "",
		id ?: "",
		url ?: ""
	)