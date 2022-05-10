package com.expostore.api.pojo.getprofile

import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.response.ImageResponse
import com.google.gson.annotations.SerializedName

data class GetProfileResponseData(
	@SerializedName("shop")
	val shop: Shop? = null,

	@SerializedName("city")
	val city: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("is_enabled_push_notify")
	val isEnabledPushNotify: Boolean? = null,

	@field:SerializedName("cause_blocked")
	val causeBlocked: String? = null,

	@field:SerializedName("is_blocked")
	val isBlocked: Boolean? = null,

	@field:SerializedName("patronymic")
	val patronymic: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("is_enabled_notify_email")
	val isEnabledNotifyEmail: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("push_token")
	val pushToken: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) {
	data class Shop(
		@field:SerializedName("owner")
		val owner: String? = null,

		@field:SerializedName("image")
		val image: ImageResponse? = null,

		@field:SerializedName("address")
		val address: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("lat")
		val lat: Double? = null,

		@field:SerializedName("long")
		val lng: Double? = null,

		@field:SerializedName("shopping_center")
		val shoppingCenter: String? = null
	)
}
