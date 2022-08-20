package com.expostore.api.request

import com.expostore.api.pojo.getcategory.Characteristic
import com.expostore.api.pojo.getcategory.CharacteristicRequest
import com.expostore.api.response.AuthorResponse
import com.expostore.api.response.ImageResponse
import com.expostore.api.response.ShopResponse
import com.expostore.model.product.PromotionModel
import com.google.gson.annotations.SerializedName

data class ProductRequest(@field:SerializedName("owner")
                          val owner: String? = null,

                          @field:SerializedName("short_description")
                          val shortDescription: String? = null,

                          @field:SerializedName("description_blocked")
                          val descriptionBlocked: String? = null,

                          @field:SerializedName("images")
                          val images: List<String>? = null,

                          @field:SerializedName("characteristics")
                          val characteristics: List<Characteristic>? = null,

                          @field:SerializedName("shop")
                          val shop: ShopResponse? = null,

                          @field:SerializedName("author")
                          val author: AuthorResponse? = null,

                          @field:SerializedName("date_created")
                          val dateCreated: String? = null,



                          @field:SerializedName("count")
                          val count: Int? = null,

                          @field:SerializedName("long_description")
                          val longDescription: String? = null,

                          @field:SerializedName("price")
                          val price: String? = null,

                          @field:SerializedName("name")
                          val name: String? = null,


                          @field:SerializedName("category")
                          val category: String? = null,

                          @field:SerializedName("is_liked")
                          val isLiked: Boolean? = null,

                          @field:SerializedName("promotion")
                          val promotion: String? = null,

                          @field:SerializedName("status")
                          val status: String? = null,

                          @field:SerializedName("communication_type")
                          val communicationType: String? = null)


fun createProductRequest(count:Int?,name:String,
                         price: String?,
                         longDescription:String?,
                         shortDescription: String?,
                         images:MutableList<String>,
                         communicationType: String?,
                         characteristics: List<CharacteristicRequest>?=null,
                         category: String?
) =
    ProductUpdateRequest(count = count,
        name = name, price = price,
        longDescription = longDescription,
        shortDescription = shortDescription,
        images = images,
        communicationType = communicationType,
        characteristics = characteristics,
        category = category
    )
data class ProductUpdateRequest(


                          @field:SerializedName("short_description")
                          val shortDescription: String? = null,



                          @field:SerializedName("images")
                          val images: List<String>? = null,

                          @field:SerializedName("characteristics")
                          val characteristics: List<CharacteristicRequest>? = null,


                          @field:SerializedName("count")
                          val count: Int? = null,

                          @field:SerializedName("long_description")
                          val longDescription: String? = null,

                          @field:SerializedName("price")
                          val price: String? = null,

                          @field:SerializedName("name")
                          val name: String? = null,


                          @field:SerializedName("category")
                          val category: String? = null,








                          @field:SerializedName("communication_type")
                          val communicationType: String? = null)

