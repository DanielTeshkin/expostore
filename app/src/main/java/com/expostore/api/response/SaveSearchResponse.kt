package com.expostore.api.response

import com.google.gson.annotations.SerializedName

data class SaveSearchResponse(
     @SerializedName("id") val id:String="",
     @SerializedName("params") val params:String="",
     @SerializedName("name") val name:String?=null,
     @SerializedName("datetime_viewed") val datetime_viewed:String="",
     @SerializedName("date_create")val  date_create: String="",
     @SerializedName("user") val user:String=""
 )
