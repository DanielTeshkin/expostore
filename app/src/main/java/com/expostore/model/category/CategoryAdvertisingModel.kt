package com.expostore.model.category

import android.os.Parcelable
import com.expostore.api.response.CategoryAdvertisingResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryAdvertisingModel(
	val image: String,
	val dateCreated: String,
	val id: String,
    val url: String
):Parcelable

val CategoryAdvertisingResponse.toModel: CategoryAdvertisingModel
	get() = CategoryAdvertisingModel(
		image ?: "",
		dateCreated ?: "",
		id ?: "",
		url ?: ""
	)