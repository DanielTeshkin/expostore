package com.expostore.api.pojo.gettenderlist

import com.expostore.api.pojo.getcategory.Characteristic
import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.response.AuthorResponse
import com.expostore.api.response.ImageResponse
import com.expostore.api.response.ShopResponse
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class TenderPage(
    @SerializedName("count")  val count :Int =0,
    @SerializedName( "next") val next:String?=null,
    @SerializedName( "previous") val previous:String?=null,
    @SerializedName( "results") val results : List<Tender>? = listOf()

)



data class Tender(
    @SerializedName("id") var id : String? = null,
    @SerializedName("is_liked") val isLiked : Boolean? = null,
    @SerializedName("author"             ) var author            : AuthorResponse?           = AuthorResponse(),
    @SerializedName("category"           ) var category          : TenderCategory?           = null,
    @SerializedName("images"             ) var images            : ArrayList<ImageResponse> = arrayListOf(),
    @SerializedName("shop"               ) var shop              : ShopResponse?             = ShopResponse(),
    @SerializedName("title"              ) var title             : String?           = null,
    @SerializedName("description"        ) var description       : String?           = null,
    @SerializedName("price_from"         ) var priceFrom         : String?           = null,
    @SerializedName("price_up_to"        ) var priceUpTo         : String?           = null,
    @SerializedName("location"           ) var location          : String?           = null,
    @SerializedName("date_created"       ) var dateCreated       : String?           = null,
    @SerializedName("lat"                ) var lat               : Double?           = null,
    @SerializedName("long"               ) var long              : Double?           = null,
    @SerializedName("count"              ) var count             : Int?              = null,
    @SerializedName("communication_type" ) var communicationType : String?           = null,
    @SerializedName("status"             ) var status            : String?           = null,
    @SerializedName("characteristics") val characteristics: List<Characteristic>?     =null

)
data class TenderResponse(
    @SerializedName("id"                 ) var id                : String?           = null,
    @SerializedName("author"             ) var author            : String           ="",
    @SerializedName("category"           ) var category          : String?          = null,
    @SerializedName("images"             ) var images            : ArrayList<String> = arrayListOf(),
    @SerializedName("shop"               ) var shop              : String?             = "",
    @SerializedName("title"              ) var title             : String?           = null,
    @SerializedName("description"        ) var description       : String?           = null,
    @SerializedName("price_from"         ) var priceFrom         : String?           = null,
    @SerializedName("price_up_to"        ) var priceUpTo         : String?           = null,
    @SerializedName("location"           ) var location          : String?           = null,
    @SerializedName("date_created"       ) var dateCreated       : String?           = null,
    @SerializedName("lat"                ) var lat               : Double?           = null,
    @SerializedName("long"               ) var long              : Double?           = null,
    @SerializedName("count"              ) var count             : Int?              = null,
    @SerializedName("communication_type" ) var communicationType : String?           = null,
    @SerializedName("status"             ) var status            : String?           = null,
    @SerializedName("end_date_of_publication"             ) val end_date_of_publication:String?=null

    )
data class TenderRequest(

    @SerializedName("category"           ) var category          : String?          = null,
    @SerializedName("images"             ) var images            : List<String> = arrayListOf(),
    @SerializedName("title"              ) var title             : String?           = null,
    @SerializedName("description"        ) var description       : String?           = null,
    @SerializedName("price_from"         ) var priceFrom         : String?           = null,
    @SerializedName("price_up_to"        ) var priceUpTo         : String?           = null,
    @SerializedName("location"           ) var location          : String?           = null,
    @SerializedName("lat"                ) var lat               : Double?           = null,
    @SerializedName("long"               ) var long              : Double?           = null,
    @SerializedName("count"              ) var count             : Int?              = null,
    @SerializedName("communication_type" ) var communicationType : String?           = null,
    @SerializedName("status"             ) var status            : String?           = null,

    )
