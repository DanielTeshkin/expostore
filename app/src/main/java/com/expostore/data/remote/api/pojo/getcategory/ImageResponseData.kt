package com.expostore.data.remote.api.pojo.getcategory

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class ImageResponseData(
    @JsonProperty("id") val id: String?,
    @JsonProperty("file") val file: String?
): Serializable

