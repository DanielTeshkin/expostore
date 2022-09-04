package com.expostore.model.category

import android.os.Parcelable
import com.expostore.data.remote.api.response.CategoryAdvertisingResponse
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