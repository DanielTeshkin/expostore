package com.expostore.data.remote.api.pojo.saveimage

import com.google.gson.annotations.SerializedName

data class SaveImageResponseData(
        @SerializedName("images_id") val id: List<String> = listOf()
)
