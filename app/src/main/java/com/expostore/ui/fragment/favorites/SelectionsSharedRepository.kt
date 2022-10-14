package com.expostore.ui.fragment.favorites

import com.expostore.model.category.SelectionModel
import kotlinx.coroutines.flow.MutableStateFlow

class SelectionsSharedRepository private constructor(){
    private val selections = MutableStateFlow(listOf<SelectionModel>())
    fun getSelections()=selections

    companion object {
        private val M_INSTANCE: SelectionsSharedRepository =
            SelectionsSharedRepository()

        @Synchronized
        fun getInstance():SelectionsSharedRepository {
            return M_INSTANCE
        }
    }
}