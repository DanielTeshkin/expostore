package com.expostore.ui.fragment.profile.profile_edit


import com.expostore.data.remote.api.pojo.getcities.City
import com.expostore.data.remote.api.pojo.getprofile.EditProfileRequest
import com.expostore.data.remote.api.response.EditResponseProfile
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.ProfileRepository
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : BaseViewModel() {
    private val _change= MutableSharedFlow<ResponseState<EditResponseProfile>>()
    val change=_change.asSharedFlow()
    private val _cities= MutableSharedFlow<ResponseState<List<City>>>()
    val cities=_cities.asSharedFlow()
    private val _citiesList= MutableStateFlow<MutableMap<String,Int>>(mutableMapOf())
    val citiesList=_citiesList.asStateFlow()
    private val _enabledState=MutableStateFlow(false)
    val enabledState=_enabledState.asStateFlow()
    private val name= MutableStateFlow("")
    private val surname= MutableStateFlow("")
    private val city= MutableStateFlow("")
    private val patronymic=MutableStateFlow("")
    private val email= MutableStateFlow("")

    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun patchProfile(requestData: EditProfileRequest) = profileRepository.patchProfile(requestData).handleResult(_change)

    fun getCities() = profileRepository.getCities().handleResult(_cities)

    fun saveCities(cities:List<City>){
        val map= mutableMapOf<String,Int>()
        cities.map {
           map.put(it.mapCity.first,it.mapCity.second)
        }
        _citiesList.value=map
    }

    fun navigateProfile(){
        navigationTo(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment())
    }

    fun toBack(){
       back()
    }



}