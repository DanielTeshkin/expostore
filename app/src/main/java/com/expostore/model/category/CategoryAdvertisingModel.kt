package com.expostore.model.category

import com.expostore.api.response.CategoryAdvertisingResponse

data class CategoryAdvertisingModel(
	val image: String,
	val dateCreated: String,
	val id: String,
	val url: String
)

val CategoryAdvertisingResponse.toModel: CategoryAdvertisingModel
	get() = CategoryAdvertisingModel(
		image ?: "",
		dateCreated ?: "",
		id ?: "",
		url ?: ""
	)