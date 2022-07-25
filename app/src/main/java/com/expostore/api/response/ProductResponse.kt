package com.expostore.api.response

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.getcategory.Characteristic
import com.expostore.api.pojo.getproduct.ProductPromotion
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.model.chats.DataMapping.FileChat
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
	@field:SerializedName("presentation_file") val presentationFile: FileChat?    = null,

	@field:SerializedName("instruction_file") val instructionFile: FileChat? = null,

	@SerializedName("articul" ) val articul : String?= null


)
data class ProductResponseUpdate(

	@SerializedName("id"                      ) var id                   : String?           = null,
	@SerializedName("promotion"               ) var promotion            : ProductPromotion?        = ProductPromotion(),
	@SerializedName("name"                    ) var name                 : String?           = null,
	@SerializedName("short_description"       ) var shortDescription     : String?           = null,
	@SerializedName("long_description"        ) var longDescription      : String?           = null,
	@SerializedName("status"                  ) var status               : String?           = null,
	@SerializedName("price"                   ) var price                : String?           = null,
	@SerializedName("count"                   ) var count                : Int?              = null,
	@SerializedName("communication_type"      ) var communicationType    : String?           = null,
	@SerializedName("date_created"            ) var dateCreated          : String?           = null,
	@SerializedName("end_date_of_publication" ) var endDateOfPublication : String?           = null,
	@SerializedName("description_blocked"     ) var descriptionBlocked   : String?           = null,
	@SerializedName("is_verified"             ) var isVerified           : Boolean?          = null,
	@SerializedName("category"                ) var category             : String?           = null,
	@SerializedName("owner"                   ) var owner                : String?           = null,
	@SerializedName("shop"                    ) var shop                 : String?           = null,
	@SerializedName("price_history"           ) var priceHistory         : ArrayList<String> = arrayListOf(),
	@SerializedName("characteristics"         ) var characteristics      : ArrayList<String> = arrayListOf(),
	@SerializedName("images"                  ) var images               : ArrayList<String> = arrayListOf()
)