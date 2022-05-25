package com.expostore.ui.state

import com.expostore.model.category.CategoryAdvertisingModel

import com.expostore.model.profile.ProfileModel

sealed class ChatState{
    data class Loading(val isLoading: Boolean) : ChatState()
    data class Error(val throwable: Throwable) : ChatState()
  //  data class SuccessMessages(val items: List<>) : MainState()
    data class SuccessAdvertising(val items: List<CategoryAdvertisingModel>) : MainState()
    data class SuccessProfile(val item: ProfileModel):MainState()
}
