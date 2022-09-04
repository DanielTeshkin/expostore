package com.expostore.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class SelectionResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("products")
	val products: List<ProductResponse>? = null,

	@field:SerializedName("date_create")
val date_create: String? = null

)