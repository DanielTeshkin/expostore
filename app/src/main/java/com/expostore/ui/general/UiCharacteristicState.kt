package com.expostore.ui.general

import com.expostore.model.category.CharacteristicFilterModel

class UiCharacteristicState() {
    fun saveFilter(inputStateModel: InputStateModel,
                   radioStateModel: RadioStateModel,
                   selectStateModel: SelectStateModel,
                   checkBoxStateModel: CheckBoxStateModel
    ):List<CharacteristicFilterModel?>{
        val list= mutableListOf<CharacteristicFilterModel>()
        inputStateModel.state.entries.map {
            list.add(
                CharacteristicFilterModel(characteristic = it.key,
                left_input = it.value.first,
                right_input = it.value.second)
            )
        }
        radioStateModel.state.entries.map {
            list.add(CharacteristicFilterModel(characteristic =it.key, selected_item = it.value))
        }
        selectStateModel.state.entries.map { list.add(
            CharacteristicFilterModel(characteristic = it.key,
                selected_items = it.value)
        ) }
        checkBoxStateModel.state.entries.map { list.add(CharacteristicFilterModel(characteristic = it.key, bool_value =it.value )) }
        return list

    }
}