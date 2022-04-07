package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("products")
	val products: List<ProductResponse>? = null
)