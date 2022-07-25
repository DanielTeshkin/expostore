package com.expostore.ui.fragment.search.filter

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.expostore.api.pojo.getcities.City
import com.expostore.api.response.SaveSearchResponse
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.search.filter.interactor.SearchFilterInteractor
import com.expostore.ui.fragment.search.filter.models.*
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val interactor: SearchFilterInteractor
) : BaseViewModel() {
    private val _cities= MutableSharedFlow<ResponseState<List<City>>>()
    val cities=_cities.asSharedFlow()
    private val _citiesList= MutableStateFlow<MutableMap<String,Int>>(mutableMapOf())
    val citiesList=_citiesList.asStateFlow()
    val flag= MutableStateFlow("")
    private val _characteristics=MutableSharedFlow<ResponseState<List<CategoryCharacteristicModel>>>()
    val characteristics=_characteristics.asSharedFlow()
    val category= MutableStateFlow<ProductCategoryModel?>(null)
    private val filterInputList= MutableStateFlow<InputStateModel>(InputStateModel(hashMapOf()))
    private val filterSelectList= MutableStateFlow<SelectStateModel>(SelectStateModel(hashMapOf()))
    private val filterRadioList= MutableStateFlow(RadioStateModel(hashMapOf()))
    private val filterCheckBox= MutableStateFlow(CheckBoxStateModel(hashMapOf()))
    private val _filterCharacteristic= MutableStateFlow<List<CharacteristicFilterModel>>(mutableListOf())
    val filterCharacteristic=_filterCharacteristic.asStateFlow()
    private val saveSearchResponse= MutableSharedFlow<ResponseState<SaveSearchResponse>>()
    private val _categories= MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories=_categories.asSharedFlow()

    override fun onStart() {
        /* no-op */
    }
    fun saveCities(cities:List<City>){
        val map= mutableMapOf<String,Int>()
        cities.map {
            map.put(it.mapCity.first,it.mapCity.second)
        }
        _citiesList.value=map
    }
    fun saveSearch(name: String,city: String,priceMin:Int?,priceMax:Int?){
        searchFilter()
        viewModelScope.launch(Dispatchers.IO) {
            val filterModel = FilterModel(
                name,
                priceMin,
                priceMax,
                city,
                category = category.value?.name,
                characteristics = filterCharacteristic.value
            )
            interactor.searchSave(filterModel, type = flag.value).handleResult(saveSearchResponse)
        }

    }
    fun getCities() = interactor.getCities().handleResult(_cities)

    fun getCategoryCharacteristic(id: String?)=
        interactor.getCategoryCategory(id).handleResult(_characteristics)

    fun addFilterInput(left:String,right:String,name:String){
        filterInputList.value.inputFilter[name] = Pair(left,right)
    }

    fun addFilterSelect(name: String,list:List<String>){
        Log.i("select",name)
        filterSelectList.value.select[name] = list
    }
    fun addFilterRadio(id:String,name: String){
        Log.i("radio",id)
            filterRadioList.value.radioFilter[name]=id
    }
    fun addFilterCheckbox(name: String,check:Boolean){
        Log.i("check",name)
        filterCheckBox.value.checkboxFilter[name]=check
    }

    fun searchFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            _filterCharacteristic.value = interactor.saveFiltersState(
                filterInputList.value,
                filterRadioList.value, filterSelectList.value, filterCheckBox.value)
        }
    }

    fun saveCategory(model:ProductCategoryModel){
        category.value=model
        getCategoryCharacteristic(model.id)
    }
    fun getListCategories()=interactor.getCategories().handleResult(_categories)

    fun navigateToSearch(){
        if (flag.value == "product")
                navigationTo(SearchFilterFragmentDirections.actionSearchFilterFragmentToSearchFragment())
            else if(flag.value=="tender") navigationTo(SearchFilterFragmentDirections.actionSearchFilterFragmentToTenderListFragment())

    }
    fun navigateToCategory(){
        navigationTo(SearchFilterFragmentDirections.actionSearchFilterFragmentToSpecificationsFragment())
    }



    fun saveFlag(name:String){
        flag.value=name
    }

}