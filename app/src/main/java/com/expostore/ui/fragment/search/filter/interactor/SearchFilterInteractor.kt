package com.expostore.ui.fragment.search.filter.interactor

import android.util.Log
import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getcities.toModel
import com.expostore.api.request.FilterRequest
import com.expostore.api.request.toRequestModel
import com.expostore.api.response.Params
import com.expostore.api.response.SaveSearchRequest
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.SearchRepository
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.ui.base.BaseInteractor
import com.expostore.ui.fragment.search.filter.models.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchFilterInteractor @Inject constructor(private val apiWorker: ApiWorker,private val categoryRepository: CategoryRepository,
                                                 private val searchRepository: SearchRepository) :
    BaseInteractor() {
    fun getCities() = flow{
        val result = handleOrEmptyList { apiWorker.getCities() }.map { it.toModel }
        emit(result)
    }

    fun searchSave(filterModel: FilterModel)=searchRepository.saveSearch(
        SaveSearchRequest(params = Params(q=filterModel.name,city=filterModel.city), bodyParams = FilterRequest(filterModel.category,
            filterModel.price_min,filterModel.price_max,filterModel.promotion,filterModel.characteristics?.map { it.toRequestModel }), typeSearch = "product")
    )
    fun getCategoryCategory(id:String?) = categoryRepository
        .getCategoryCharacteristic(id?:"")

         fun saveFilter(inputStateModel: InputStateModel,
                        radioStateModel: RadioStateModel,
                        selectStateModel: SelectStateModel,
         checkBoxStateModel: CheckBoxStateModel):List<CharacteristicFilterModel>{
             val list= mutableListOf<CharacteristicFilterModel>()
         inputStateModel.inputFilter.entries.map {
                 list.add(CharacteristicFilterModel(characteristic = it.key,
                     left_input = it.value.first,
                     right_input = it.value.second))
             Log.i("top", it.value.first)

             }
             radioStateModel.radioFilter.entries.map {
                 list.add(CharacteristicFilterModel(characteristic =it.key, selected_item = it.value))
             }
             selectStateModel.select.entries.map { list.add(CharacteristicFilterModel(characteristic = it.key,
                 selected_items = it.value)
             ) }
             checkBoxStateModel.checkboxFilter.entries.map { list.add(CharacteristicFilterModel(characteristic = it.key, bool_value =it.value )) }
             return list


         }
        }

