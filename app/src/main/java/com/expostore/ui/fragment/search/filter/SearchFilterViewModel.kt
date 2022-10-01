package com.expostore.ui.fragment.search.filter

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.expostore.data.remote.api.pojo.getcities.City
import com.expostore.data.remote.api.response.SaveSearchResponse
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.base.vms.CharacteristicViewModel
import com.expostore.ui.fragment.search.filter.interactor.SearchFilterInteractor
import com.expostore.ui.fragment.search.filter.models.*
import com.expostore.ui.general.CheckBoxStateModel
import com.expostore.ui.general.InputStateModel
import com.expostore.ui.general.RadioStateModel
import com.expostore.ui.general.SelectStateModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    override val interactor: SearchFilterInteractor
) : CharacteristicViewModel() {
    private val _cities= MutableSharedFlow<ResponseState<List<City>>>()
    val cities=_cities.asSharedFlow()
    private val _citiesList= MutableStateFlow<MutableMap<String,Int>>(mutableMapOf())
    val citiesList=_citiesList.asStateFlow()
    val flag= MutableStateFlow("")
    private val _filterCharacteristic= MutableStateFlow<List<CharacteristicFilterModel>>(mutableListOf())
    val filterCharacteristic=_filterCharacteristic.asStateFlow()
    private val saveSearchResponse= MutableSharedFlow<ResponseState<SaveSearchResponse>>()


    override fun onStart() {
        /* no-op */
    }
    init {
        getCities()
        getCategories()
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
                category = category.value,
                characteristics = filterCharacteristic.value
            )
            interactor.searchSave(filterModel, type = flag.value).handleResult(saveSearchResponse)
        }

    }
    fun getCities() = interactor.getCities().handleResult(_cities)






    fun searchFilter(){
        _filterCharacteristic.value = saveCharacteristicsState()
    }

    fun getFiltersAndNavigateToSearch(name: String,priceMin: Int?,priceMax: Int?,city: String){
        val characteristics=saveCharacteristicsState()
        navigateToSearch(FilterModel(name,priceMin,priceMax,city,  characteristics = characteristics))
    }




    private fun navigateToSearch(filterModel: FilterModel){
        if (flag.value == "product")
                navigationTo(SearchFilterFragmentDirections.actionSearchFilterFragmentToSearchFragment(filterModel))
            else if(flag.value=="tender") navigationTo(SearchFilterFragmentDirections.actionSearchFilterFragmentToTenderListFragment(filterModel))

    }

   fun navigateToMapChoice()=navigationTo(SearchFilterFragmentDirections.actionSearchFilterFragmentToMapChoice())


    fun saveFlag(name:String){
        flag.value=name
    }

}