package com.expostore.api.request

import com.google.gson.annotations.SerializedName

data class ProductsSelection(
    @SerializedName("products") val products:List<String>
)
