package com.expostore.ui.controllers

import android.content.Context
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.toRequestCreate
import com.expostore.ui.general.CharacteristicState
import com.expostore.ui.general.CharacteristicsStateModel
import com.expostore.ui.general.UiCharacteristicState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseCharacteristicController(context:Context) : ControllerUI(context), CharacteristicState {
    private val selectList = mutableListOf<String>()
    private val _characteristicState= MutableStateFlow(CharacteristicsStateModel())
    protected val characteristicState=_characteristicState.asStateFlow()
    val list= mutableListOf("")

    override fun inputListener(left: String, right: String?, name: String) =_characteristicState.value.inputStateModel.state.set(name, Pair(left,right))
    override fun radioListener(id: String, name: String) = _characteristicState.value.radioStateModel.state.set(name, id)
    override fun selectListener(id: String, name: String, checked: Boolean) {
        when(checked){
            true-> selectList.add(id)
            false->selectList.remove(id)
        }
        _characteristicState.value.selectStateModel.state[name] = selectList
    }

    override fun checkBoxListener(name: String, checked: Boolean) =_characteristicState.value
    .checkBoxStateModel.state.set(name, checked)



    protected fun characteristicLoad()=saveCharacteristicsState().map { it?.toRequestCreate }
    private fun saveCharacteristicsState(

    ): List<CharacteristicFilterModel?> =
        UiCharacteristicState().saveFilter(_characteristicState.value.inputStateModel,
            _characteristicState.value.radioStateModel,
            _characteristicState.value.selectStateModel,
            _characteristicState.value.checkBoxStateModel
        )

}