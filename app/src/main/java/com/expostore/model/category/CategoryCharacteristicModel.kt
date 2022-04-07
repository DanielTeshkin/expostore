package com.expostore.model.category

import com.expostore.api.response.CategoryCharacteristicResponse

data class CategoryCharacteristicModel(
    val listValue: List<String> = emptyList(),
    val name: String = "",
    val id: String = "",
    val leftName: String = "",
    val rightName: String = "",
    val fieldName: String = "",
    val type: CharacteristicType = CharacteristicType.NONE
)

val CategoryCharacteristicResponse.toModel: CategoryCharacteristicModel
    get() = CategoryCharacteristicModel(
        listValue.orEmpty(),
        name ?: "",
        id ?: "",
        leftName ?: "",
        rightName ?: "",
        fieldName ?: "",
        CharacteristicType.valueOf(type.orEmpty().uppercase())
    )

enum class CharacteristicType {
    NONE, SINGLE_INPUT, DOUBLE_INPUT,SELECT, CHECKBOX, RADIO
}
