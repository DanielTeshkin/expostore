package com.expostore.api.pojo.getcategory


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class CategoryProduct(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("images") var images: ArrayList<ImageResponseData>?,
    @SerializedName("price") val price: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("promotion") val promotion: String?,
    @SerializedName("is_liked") val like: Boolean?)
