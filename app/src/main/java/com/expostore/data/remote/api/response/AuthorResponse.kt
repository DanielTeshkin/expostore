package com.expostore.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class AuthorResponse(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("avatar")
	val avatar:ImageResponse?=ImageResponse()
)
