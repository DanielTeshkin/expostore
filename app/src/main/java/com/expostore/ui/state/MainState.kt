package com.expostore.ui.state


import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.SelectionModel

import com.expostore.model.profile.ProfileModel


sealed class MainState {
    data class Loading(val isLoading: Boolean) : MainState()
    data class Error(val throwable: Throwable) : MainState()
    data class SuccessCategory(val items: List<SelectionModel>) : MainState()
    data class SuccessAdvertising(val items: List<CategoryAdvertisingModel>) : MainState()
    data class SuccessProfile(val item:ProfileModel):MainState()
}