package com.expostore.model

import android.os.Parcelable
import com.expostore.api.response.SaveSearchResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaveSearchModel(
   val id:String,
    val params:String,
   val name:String?=null,
     val datetime_viewed:String,
   val  date_create: String,
   val user:String
):Parcelable

val SaveSearchResponse.toModel :SaveSearchModel
get() = SaveSearchModel(
    id=id,
    params=params,
    name,
    datetime_viewed=datetime_viewed,
    date_create, user
)
