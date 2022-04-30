package com.expostore.api.pojo.getcategory

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("author")  val author: Author,
    val category: String,
    val characteristics: List<Characteristic>,
    val communication_type: String,
    val count: Int,
    @SerializedName("date_created")  val date_created: String,
    @SerializedName("description_blocked")  val description_blocked: String,
    @SerializedName("end_date_of_publication") val end_date_of_publication: Any,
    @SerializedName("id") val id: String,
    val images: List<Image>,
    val is_liked: Boolean,
    val is_verified: Boolean,
    val long_description: String,
    val name: String,
    @SerializedName("owner")   val owner: String,
    val price: String,
    val price_history: List<Any>,
    val promotion: Any,
    val rating: Double,
    @SerializedName("shop") val shop: Shop,
    @SerializedName("short_description")val short_description: String,
    val status: String
)