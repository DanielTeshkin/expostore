package com.expostore.api.pojo.getcities

import com.fasterxml.jackson.annotation.JsonProperty

data class City(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String
)
{
    override fun toString(): String {
        return name
    }
}
