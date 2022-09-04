package com.expostore.data.remote.api.pojo.comparison

import com.expostore.data.remote.api.pojo.getcategory.Characteristic
import com.expostore.data.remote.api.response.CharacteristicComparison
import com.google.gson.annotations.SerializedName

data class ComparisonProductData(
    @SerializedName("product_id") val id:String =""
)

data class ComparisonResult (

    @SerializedName("product_characteristics") val product_characteristics : List<ComparisonResponse> = listOf(),
    @SerializedName("different_characteristics") val different_characteristics : List<ComparisonResponse> = listOf()
)
data class ComparisonResponse(
    @SerializedName("id") val id: String="",
    @SerializedName("characteristics") val characteristics: List<CharacteristicComparison> = listOf()
    
)