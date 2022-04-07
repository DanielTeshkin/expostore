package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class CategoryCharacteristicResponse(

	@field:SerializedName("list_value")
	val listValue: List<String>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("left_field_name")
	val leftName: String? = null,

	@field:SerializedName("right_field_name")
	val rightName: String? = null,

	@field:SerializedName("field_name")
	val fieldName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
