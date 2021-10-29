package com.expostore.api.pojo.getcategory

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class CategoryProduct(
        @JsonProperty("id") val id: String?,
        @JsonProperty("name") val name: String?,
        @JsonProperty("images") var images: ArrayList<CategoryProductImage>?,
        @JsonProperty("price") val price: String?,
        @JsonProperty("rating") val rating: String?,
        @JsonProperty("promotion") val promotion: String?,
        @JsonProperty("is_liked") val like: Boolean
): Serializable
