package com.expostore.data.remote.api.request

import com.google.gson.annotations.SerializedName

data class ProductsSelection(
    @SerializedName("products") val products:List<String>
)
