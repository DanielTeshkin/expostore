package com.expostore.ui.fragment.search.filter.interactor

import android.util.Log
import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getcities.toModel
import com.expostore.api.request.FilterRequest
import com.expostore.api.request.toRequestModel
import com.expostore.api.response.Params
import com.expostore.api.response.SaveSearchRequest
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.data.repositories.SearchRepository
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.ui.base.BaseInteractor
import com.expostore.ui.base.UiCharacteristicState
import com.expostore.ui.fragment.search.filter.models.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchFilterInteractor @Inject constructor(private val categoryRepository: CategoryRepository,
                                                 private val searchRepository: SearchRepository,
                                                 private val profileRepository: ProfileRepository) :
    BaseInteractor() {
    fun getCities() =profileRepository.getCities()

    fun getCategories() = categoryRepository.getCategories()

    fun searchSave(filterModel: FilterModel, type: String) = searchRepository.saveSearch(
        SaveSearchRequest(params = Params(q = filterModel.name, city = filterModel.city),
            bodyParams = FilterRequest(filterModel.category,
                filterModel.price_min,
                filterModel.price_max,
                filterModel.promotion,
                filterModel.characteristics?.map { it.toRequestModel }),
            typeSearch = type
        )
    )

    fun getCategoryCategory(id: String?) = categoryRepository
        .getCategoryCharacteristic(id ?: "")

    fun saveFiltersState(inputStateModel: InputStateModel, radioStateModel: RadioStateModel,
        selectStateModel: SelectStateModel,
        checkBoxStateModel: CheckBoxStateModel
    ): List<CharacteristicFilterModel> =
         UiCharacteristicState().saveFilter(
            inputStateModel,
            radioStateModel,
            selectStateModel,
            checkBoxStateModel
        )

}

