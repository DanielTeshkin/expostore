package com.expostore.api.pojo.productcategory

import android.os.Parcel
import android.os.Parcelable
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ProductCategory(
    @SerializedName("id") val id: String?="",
    @SerializedName("name") val name: String?="",
    @SerializedName("sorting_number") val sortingNumber: Int?=0,
    @SerializedName("parent") val parent: String?="",
    @SerializedName("child_category") val child_category: List<ProductCategory>? = null,
    @SerializedName("have_child")  val have_child:Boolean = false

    )

