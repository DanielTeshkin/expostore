package com.expostore.ui.base.vms

import android.util.Log
import com.expostore.data.remote.api.pojo.getcategory.CharacteristicRequest
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.toRequestCreate
import com.expostore.model.product.Character
import com.expostore.ui.base.interactors.CharacteristicsInteractor
import com.expostore.ui.general.CharacteristicsStateModel
import com.expostore.ui.general.UiCharacteristicState
import com.expostore.ui.general.toCharacteristicState
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class CharacteristicViewModel : BaseViewModel() {
    abstract val  interactor: CharacteristicsInteractor
    private val selectList = mutableListOf<String>()
    private val _characteristicState= MutableStateFlow(CharacteristicsStateModel())
     val characteristicState=_characteristicState.asStateFlow()
    private val _categories=MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories=_categories.asSharedFlow()
    private val _category = MutableStateFlow("")
    val category=_category.asStateFlow()
    private val _characteristics= MutableSharedFlow<ResponseState<List<CategoryCharacteristicModel>>>()
    val characteristics=_characteristics.asSharedFlow()
    fun getCategories()=interactor.getCategories().handleResult(_categories)
    fun loadCategoryCharacteristics(id: String)=interactor.getCategoryCharacteristic(id).handleResult(_characteristics)
     fun saveCharacteristic(characteristics:List<Character>){
        _characteristicState.value=characteristics.toCharacteristicState()
    }

     fun inputListener(left: String, right: String?, name: String) =_characteristicState.value.inputStateModel.state.set(name, Pair(left,right))
     fun radioListener(id: String, name: String) = _characteristicState.value.radioStateModel.state.set(name, id)
     fun selectListener(id: String, name: String, checked: Boolean) {
        when(checked){
            true-> selectList.add(id)
            false->selectList.remove(id)
        }
        _characteristicState.value.selectStateModel.state[name] = selectList
    }

     fun checkBoxListener(name: String, checked: Boolean) =_characteristicState.value
        .checkBoxStateModel.state.set(name, checked)

    protected fun characteristicLoad(): List<CharacteristicRequest> = saveCharacteristicsState().map { it.toRequestCreate }
    fun saveCategory(id:String){
        _category.value=id
        loadCategoryCharacteristics(id)
    }
    protected fun saveCharacteristicsState() =
        UiCharacteristicState().saveFilter(_characteristicState.value.inputStateModel,
            _characteristicState.value.radioStateModel,
            _characteristicState.value.selectStateModel,
            _characteristicState.value.checkBoxStateModel
        )
    fun navigateToBack()=navController.popBackStack()

    override fun onStart() {
       Log.i("","fff")
    }
}