package com.expostore.api.pojo.getcategory

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Category(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("products") val products: ArrayList<CategoryProduct>?,
    @JsonProperty("count") val count: Int?
): Serializable
