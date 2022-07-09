package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class CategoryCharacteristicResponse(

	@field:SerializedName("list_value")
	val listValue: List<Value>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null

)

data class Value(
	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("id")
	val id: String? = null,
)
