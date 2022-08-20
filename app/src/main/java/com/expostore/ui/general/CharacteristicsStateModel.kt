package com.expostore.ui.general

import com.expostore.model.product.Character

data class CharacteristicsStateModel(
    val inputStateModel: InputStateModel?= null,
    val selectStateModel: SelectStateModel?=null,
    val radioStateModel: RadioStateModel?=null,
    val checkBoxStateModel: CheckBoxStateModel?=null
    )

fun List<Character>.toCharacteristicState(): CharacteristicsStateModel {
    val inputStateModel = InputStateModel(hashMapOf())
    val radioStateModel = RadioStateModel(hashMapOf())
    val checkBoxStateModel= CheckBoxStateModel(hashMapOf())
    val selectStateModel=SelectStateModel(hashMapOf())

    map {
        val characteristic=it.characteristic
        when (characteristic?.type) {
            "input" -> inputStateModel.state[characteristic.name?:""] = Pair(it.char_value?:"","")
            "radio" -> radioStateModel.state[characteristic.name?:""]=it.selected_item?.value?:""
            "select" -> selectStateModel.state[characteristic.name?:""]=it.selected_items?.map {mean-> mean.id }?: listOf()
            else -> checkBoxStateModel.state[characteristic?.name?:""]=it.bool_value?:false
        }
    }
    return CharacteristicsStateModel(inputStateModel, radioStateModel = radioStateModel, checkBoxStateModel = checkBoxStateModel,
    selectStateModel = selectStateModel
        )
}