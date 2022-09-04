package com.expostore.model.product

import android.os.Parcelable
import com.expostore.data.remote.api.response.PriceHistoryResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PriceHistoryDataModel(
    val list: List<PriceHistoryModel>
):Parcelable

@Parcelize
data class PriceHistoryModel(
     val price:String,
    val date_create:String,
):Parcelable

val PriceHistoryResponse.toModel :PriceHistoryModel
get() = PriceHistoryModel(price,date_create)
