package com.expostore.api.pojo.gettenderlist

import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.response.AuthorResponse
import com.expostore.api.response.ImageResponse
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class TenderPage(
    @SerializedName("count")  val count :Int =0,
    @SerializedName( "next") val next:String?=null,
    @SerializedName( "previous") val previous:String?=null,
    @SerializedName( "results") val results : List<Tender>? = listOf()

)



data class Tender(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price_from") val priceFrom: String?,
    @SerializedName("price_up_to") val priceUpTo: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("author") val author:AuthorResponse,
    @SerializedName("date_created") val  date_created :String,
    @SerializedName("images") val images: ArrayList<ImageResponse>,
    @SerializedName("category") val category: TenderCategory?=null,
    @SerializedName("cunt") val count:Int,
)
