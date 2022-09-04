package com.expostore.data.remote.api.response

import com.expostore.data.remote.api.request.FilterRequest
import com.google.gson.annotations.SerializedName

data class SaveSearchResponse(
     @SerializedName("id") val id:String="",
     @SerializedName("params") val params:Params?=Params(),
     @SerializedName("category") val name:String?=null,
     @SerializedName("datetime_viewed") val datetime_viewed:String="",
     @SerializedName("date_create")val  date_create: String="",
     @SerializedName("user") val user:String="",
     @SerializedName("body_params" )var bodyParams : FilterRequest? = FilterRequest(),
     @SerializedName("type_search" ) var typeSearch : String?     = null
 )
data class SaveSearchRequest(
    @SerializedName("params"      ) var params     : Params?     = Params(),
    @SerializedName("body_params" ) var bodyParams : FilterRequest? = FilterRequest(),
    @SerializedName("type_search" ) var typeSearch : String?     = null
)
data class Params(
    @SerializedName("q") var q: String? = null,
    @SerializedName("sort") var sort: String? = null,
    @SerializedName("city")val city: String?=null
)