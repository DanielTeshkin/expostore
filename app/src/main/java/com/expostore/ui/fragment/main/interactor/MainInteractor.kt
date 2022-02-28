package com.expostore.ui.fragment.main.interactor

import com.expostore.api.ApiWorker
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.CategoryModel
import com.expostore.model.category.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class MainInteractor @Inject constructor(private val apiWorker: ApiWorker) : BaseInteractor() {
    
    private suspend fun getCategories() = handleOrEmptyList { apiWorker.getCategories() }.map { it.toModel }

    private suspend fun getAdvertising() =
        handleOrEmptyList { apiWorker.getCategoryAdvertising() }.map { it.toModel }

    fun load() = flow {
        emit(MainData.Categories(getCategories()))
        emit(MainData.Advertising(getAdvertising()))
    }

    sealed class MainData {
        data class Categories(val items: List<CategoryModel>) : MainData()
        data class Advertising(val items: List<CategoryAdvertisingModel>) : MainData()
    }
}