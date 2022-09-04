package com.expostore.data.remote.api.response

import com.expostore.data.remote.api.pojo.getcategory.Characteristic
import com.expostore.data.remote.api.pojo.getcategory.CharacteristicRequest
import com.expostore.data.remote.api.pojo.getchats.ChatFile
import com.expostore.data.remote.api.pojo.getproduct.ProductPromotion
import com.expostore.data.remote.api.pojo.getreviews.Review
import com.expostore.data.remote.api.pojo.productcategory.ProductCategory
import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("owner")
	val owner: String? = null,

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("description_blocked")
	val descriptionBlocked: String? = null,

	@field:SerializedName("images")
	val images: List<ImageResponse> = listOf(),

	@field:SerializedName("characteristics")
	val characteristics: List<Characteristic>? =listOf(),

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
	val category: ProductCategory? = null,

	@field:SerializedName("is_liked")
	val isLiked: Boolean? = null,

	@field:SerializedName("promotion")
	val promotion: ProductPromotion? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("communication_type")
	val communicationType: String? = null,
	@field:SerializedName("presentation_file") val presentationFile: ChatFile?    = null,

	@field:SerializedName("instruction_file") val instructionFile: ChatFile? = null,
	@field:SerializedName("articul" ) val articul : String?= null,
	@field:SerializedName("reviews" ) val reviews:List<Review> = listOf(),
	@field:SerializedName("qrcode" ) val qrcode:QrCodeResponse=QrCodeResponse(),
	@field:SerializedName("elected" ) val elected:ElectedResponse?=null

	)
   data class ElectedResponse(
	   @field:SerializedName("id" ) val id:String="",
	   @field:SerializedName("notes" ) val notes:String=""
   )



data class CreateResponseProduct(

	@field:SerializedName("owner")
	val owner: String? = null,

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("description_blocked")
	val descriptionBlocked: String? = null,

	@field:SerializedName("images")
	val images: List<String> = listOf(),


	@field:SerializedName("shop")
	val shop: String? = null,



	@field:SerializedName("date_created")
	val dateCreated: String? = null,



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
	val promotion: ProductPromotion? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("communication_type")
	val communicationType: String? = null,
	@field:SerializedName("presentation_file") val presentationFile: ChatFile?    = null,

	@field:SerializedName("instruction_file") val instructionFile: ChatFile? = null,

	@field:SerializedName("qrcode" ) val qrcode:QrCodeResponse=QrCodeResponse()
)
data class QrCodeResponse(
	@field:SerializedName("product")
	val product: String = "",
	@field:SerializedName("qr_code_image")
	val qr_code_image: String = "",
)

data class PersonalProductRequest(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("images")
	val images: List<String> = listOf(),

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone_number")
	val phone:String?=null,

	@field:SerializedName("name_contact")
	val name_contact: String? = null,

	@field:SerializedName("characteristics")
	val characteristics: List<CharacteristicRequest?>? = null,

	@field:SerializedName("price")
	val price: String? = null,


	@field:SerializedName("category")
	val category: String? = null,
)