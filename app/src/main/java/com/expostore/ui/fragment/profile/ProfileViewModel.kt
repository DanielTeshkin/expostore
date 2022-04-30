package com.expostore.ui.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ProfileViewModel constructor( private val profileRepository: ProfileRepository) : BaseViewModel() {
         private val _profile=MutableSharedFlow<ResponseState<ProfileModel>>()
    val profile=_profile.asSharedFlow()
    private val _title=MutableStateFlow<String>("Создать магазин")
    val title=_title.asStateFlow()

    init {
        loadProfile()}

   private fun loadProfile(){
        profileRepository.getProfile().handleResult(_profile)
    }
    fun check(model:ProfileModel){
        if(model.shop!=null)
            _title.value="Редактировать магазин"
    }


    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun navigateEditProfile(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
    }
    fun navigateMyProduct(exist:Boolean){
        when(exist){
        true ->navigationTo(ProfileFragmentDirections.actionProfileFragmentToMyProductsFragment())
            else -> {}
        }
    }

}