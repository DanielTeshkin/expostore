package com.expostore.model

import android.os.Parcelable
import com.expostore.api.response.Params
import com.expostore.api.response.SaveSearchResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaveSearchModel(
   val id:String,
    val params:ParamsModel?=ParamsModel(),
   val name:String?=null,
     val datetime_viewed:String,
   val  date_create: String,
   val user:String
):Parcelable

val SaveSearchResponse.toModel :SaveSearchModel
get() = SaveSearchModel(
    id=id,
    params=params?.toModel,
    name,
    datetime_viewed=datetime_viewed,
    date_create, user
)
@Parcelize
data class ParamsModel(
   var q: String? = null,
     var sort: String? = null,
   val city: String?=null
):Parcelable
val Params.toModel:ParamsModel
get() = ParamsModel(q, sort, city)
