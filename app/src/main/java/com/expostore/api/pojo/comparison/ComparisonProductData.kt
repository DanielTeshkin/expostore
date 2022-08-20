package com.expostore.api.pojo.comparison

import com.expostore.api.pojo.getcategory.Characteristic
import com.google.gson.annotations.SerializedName

data class ComparisonProductData(
    @SerializedName("product_id") val id:String
)

data class ComparisonResult (

    @SerializedName("product_characteristics") val product_characteristics : List<Characteristic>,
    @SerializedName("different_characteristics") val different_characteristics : List<Characteristic>
)
