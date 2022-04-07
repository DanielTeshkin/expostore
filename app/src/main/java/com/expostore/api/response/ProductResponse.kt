package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("owner")
	val owner: String? = null,

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("description_blocked")
	val descriptionBlocked: String? = null,

	@field:SerializedName("images")
	val images: List<ImageResponse>? = null,

	@field:SerializedName("characteristics")
	val characteristics: List<String>? = null,

	@field:SerializedName("shop")
	val shop: ShopResponse? = null,

	@field:SerializedName("author")
	val author: AuthorResponse? = null,

	@field:SerializedName("date_created")
	val dateCreated: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("long_description")
	val longDescription: String? = null,

	@field:SerializedName("is_verified")
	val isVerified: Boolean? = null,

	@field:SerializedName("price_history")
	val priceHistory: List<String>? = null,

	@field:SerializedName("end_date_of_publication")
	val endDateOfPublication: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("is_liked")
	val isLiked: Boolean? = null,

	@field:SerializedName("promotion")
	val promotion: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("communication_type")
	val communicationType: String? = null
)