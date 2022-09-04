package com.expostore.model.category

import android.os.Parcelable
import com.expostore.data.remote.api.response.CategoryCharacteristicResponse
import com.expostore.data.remote.api.response.Value
import kotlinx.android.parcel.Parcelize

data class CategoryCharacteristicModel(
    val listValue: List<ValueModel> = emptyList(),
    val name: String = "",
    val id: String = "",
    val type: String = ""
)

val CategoryCharacteristicResponse.toModel: CategoryCharacteristicModel
    get() = CategoryCharacteristicModel(
        listValue.orEmpty().map { it.toModel },
        name ?: "",
        id ?: "",
       type?:""
    )
@Parcelize
data class ValueModel(
    val value: String? = null,
    val id: String? = null,
):Parcelable
val Value.toModel:ValueModel
get() = ValueModel(value, id)

enum class CharacteristicType {
    NONE, INPUT, DOUBLE_INPUT,SELECT, CHECKBOX, RADIO
}
