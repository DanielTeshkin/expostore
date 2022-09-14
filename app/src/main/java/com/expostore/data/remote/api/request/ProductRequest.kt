package com.expostore.data.remote.api.request

import com.expostore.data.remote.api.pojo.getcategory.Characteristic
import com.expostore.data.remote.api.pojo.getcategory.CharacteristicRequest
import com.expostore.data.remote.api.response.AuthorResponse
import com.expostore.data.remote.api.response.ShopResponse
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
                         characteristics: List<CharacteristicRequest?> = listOf() ,
                         category: String?,
                         presentation: String?=null,
                         instruction: String?=null
) =
    ProductUpdateRequest(count = count,
        name = name, price = price,
        longDescription = longDescription,
        shortDescription = shortDescription,
        images = images,
        communicationType = communicationType,
        characteristics = characteristics,
        category = category,
        presentation = presentation,
        instruction = instruction
    )
data class ProductUpdateRequest(


                          @field:SerializedName("short_description")
                          val shortDescription: String? = null,

                          @field:SerializedName("images")
                          val images: List<String>? = null,

                          @field:SerializedName("characteristics")
                          val characteristics: List<CharacteristicRequest?>? = null,


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
                          val communicationType: String? = null,
                          @field:SerializedName("instruction")
                          val instruction: String? = null,

    @field:SerializedName("presentation")
val presentation: String? = null





)

