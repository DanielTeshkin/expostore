package com.expostore.ui.general

import com.expostore.model.product.Character

data class CharacteristicsStateModel(
    val inputStateModel: InputStateModel= InputStateModel(hashMapOf()),
    val selectStateModel: SelectStateModel= SelectStateModel(hashMapOf()),
    val radioStateModel: RadioStateModel= RadioStateModel(hashMapOf()),
    val checkBoxStateModel: CheckBoxStateModel= CheckBoxStateModel(hashMapOf())
    )

fun List<Character>.toCharacteristicState(): CharacteristicsStateModel {
    val inputStateModel = InputStateModel(hashMapOf())
    val radioStateModel = RadioStateModel(hashMapOf())
    val checkBoxStateModel= CheckBoxStateModel(hashMapOf())
    val selectStateModel=SelectStateModel(hashMapOf())

    map {
        val characteristic=it.characteristic
        when (characteristic?.type) {
            "input" -> inputStateModel.state[characteristic.id?:""] = Pair(it.char_value?:"","")
            "radio" -> radioStateModel.state[characteristic.id?:""]=it.selected_item?.id?:""
            "select" -> selectStateModel.state[characteristic.id?:""]=it.selected_items?.map {mean-> mean.id }?: listOf()
            else -> checkBoxStateModel.state[characteristic?.id?:""]=it.bool_value?:false
        }
    }
    return CharacteristicsStateModel(inputStateModel, radioStateModel = radioStateModel, checkBoxStateModel = checkBoxStateModel,
    selectStateModel = selectStateModel
        )
}