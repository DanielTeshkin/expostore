package com.expostore.ui.fragment.main.interactor

import com.expostore.api.ApiWorker
import com.expostore.api.ApiWorkerImpl
import com.expostore.model.category.toModel
import com.expostore.ui.base.BaseInteractor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class MainInteractor @Inject constructor(private val apiWorker: ApiWorker): BaseInteractor() {
    fun geCategories() = flow {
        val result = handleOrEmptyList { apiWorker.getCategories() }
        emit(result.map { it.toModel })
    }
}