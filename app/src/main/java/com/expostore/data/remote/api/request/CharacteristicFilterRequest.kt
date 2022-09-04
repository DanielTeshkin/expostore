package com.expostore.data.remote.api.request

import com.expostore.model.category.CharacteristicFilterModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.maps.model.Distance


data class FilterRequest(
    @SerializedName("category") val category: String?=null,
    @SerializedName("price_min") val price_min: Int?=null,
    @SerializedName("price_max") val price_max: Int?=null,
    @SerializedName("promotion") val promotion: Boolean?=null,
    @SerializedName("characteristics") val characteristics: List<CharacteristicFilterRequest>?=null,
    @SerializedName("q") val q: String?=null,
    @SerializedName("lat") val lat: Double?=null,
    @SerializedName("long") val long: Double?=null,
    @SerializedName("city") val city: String?=null,
    @SerializedName("sort") val sort: List<String?>? = null,
    @SerializedName("distance") val distance: Double? = null,

)

data class CharacteristicFilterRequest(
    @SerializedName("characteristic") val characteristic : String?,
    @SerializedName("char_value") val char_value : String?,
    @SerializedName("bool_value") val bool_value : Boolean?,
    @SerializedName("selected_item") val selected_item : String?,
    @SerializedName("selected_items") val selected_items : List<String>?,
    @SerializedName("left_input") val left_input : String?,
    @SerializedName("right_input") val right_input : String?

){
    override fun toString(): String {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val tut = this
        val jsonTut: String = gson.toJson(tut)
        println(jsonTut)
        val jsonTutPretty: String = gsonPretty.toJson(tut)

      return jsonTutPretty
    }
}
data class RequestCharacteristic(
    @SerializedName("characteristics") val characteristics : List<CharacteristicFilterRequest>
)

fun List<CharacteristicFilterRequest>.toStroke():String{
    val gson = Gson()
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    val jsonTutsList: String = gson.toJson(this)
    println(jsonTutsList)

    return gsonPretty.toJson(this)
}

val CharacteristicFilterModel.toRequestModel :CharacteristicFilterRequest
get() = CharacteristicFilterRequest(characteristic, char_value, bool_value, selected_item, selected_items, left_input, right_input)
