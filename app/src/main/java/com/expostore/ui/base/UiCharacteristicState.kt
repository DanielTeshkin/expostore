package com.expostore.ui.base

import android.util.Log
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.ui.fragment.search.filter.models.CheckBoxStateModel
import com.expostore.ui.fragment.search.filter.models.InputStateModel
import com.expostore.ui.fragment.search.filter.models.RadioStateModel
import com.expostore.ui.fragment.search.filter.models.SelectStateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UiCharacteristicState() {
    fun saveFilter(inputStateModel: InputStateModel,
                   radioStateModel: RadioStateModel,
                   selectStateModel: SelectStateModel,
                   checkBoxStateModel: CheckBoxStateModel
    ):List<CharacteristicFilterModel>{
        val list= mutableListOf<CharacteristicFilterModel>()
        inputStateModel.inputFilter.entries.map {
            list.add(
                CharacteristicFilterModel(characteristic = it.key,
                left_input = it.value.first,
                right_input = it.value.second)
            )
        }
        radioStateModel.radioFilter.entries.map {
            list.add(CharacteristicFilterModel(characteristic =it.key, selected_item = it.value))
        }
        selectStateModel.select.entries.map { list.add(
            CharacteristicFilterModel(characteristic = it.key,
            selected_items = it.value)
        ) }
        checkBoxStateModel.checkboxFilter.entries.map { list.add(CharacteristicFilterModel(characteristic = it.key, bool_value =it.value )) }
        return list

    }
}