package com.expostore.ui.state

import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.CategoryModel

/**
 * @author Fedotov Yakov
 */
sealed class MainState {
    data class Loading(val isLoading: Boolean) : MainState()
    data class Error(val throwable: Throwable) : MainState()
    data class SuccessCategory(val items: List<CategoryModel>) : MainState()
    data class SuccessAdvertising(val items: List<CategoryAdvertisingModel>) : MainState()
}