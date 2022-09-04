package com.expostore.ui.fragment.authorization.registration.completion

import com.expostore.data.remote.api.pojo.editprofile.EditProfileRequestData
import com.expostore.data.remote.api.pojo.editprofile.EditProfileResponseData
import com.expostore.data.remote.api.pojo.getcities.City
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.ui.base.BaseViewModel

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class CompletionViewModel @Inject constructor(private val registration: AuthorizationRepository,private val profileRepository: ProfileRepository): BaseViewModel() {

    private val _ui= MutableSharedFlow<ResponseState<EditProfileResponseData>>()
    val ui=_ui.asSharedFlow()
    private val _cities= MutableSharedFlow<ResponseState<List<City>>>()
    val cities=_cities.asSharedFlow()
    private val _citiesList= MutableStateFlow<MutableMap<String,Int>>(mutableMapOf())
  private  val citiesList=_citiesList.asStateFlow()
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
          fun editProfile(){
              val requestData=EditProfileRequestData(last_name = surname.value, first_name = name.value,
                  patronymic = patronymic.value, city = city.value, email = email.value)
              registration.editProfile(requestData).handleResult(_ui) {
                  navigationTo(CompletionFragmentDirections.actionCompletionFragmentToMainFragment())
              }
          }

   private fun updateEnableState(state:Boolean){
           _enabledState.value=state
    }
    fun updateSurname(mean:String){
        surname.value=mean
         checkFieldsState()
    }
    fun updateName(mean: String){
        name.value=mean
        checkFieldsState()
    }
    fun updateCity(mean: String){
        city.value=mean
        checkFieldsState()
    }
    fun updateEmail(mean: String){
        email.value=mean
        checkFieldsState()
    }
    fun updatePatronymic(mean: String){
        patronymic.value=mean
        checkFieldsState()
    }


    private fun checkFieldsState(){
        updateEnableState(surname.value.isNotEmpty()&&name.value.isNotEmpty()&&checkCity())
    }

    fun backEnd(){
        navigationTo(CompletionFragmentDirections.actionCompletionFragmentToCreatePasswordFragment())
    }
   private fun checkCity():Boolean{
        return city.value.isNotEmpty()&&citiesList.value.containsKey(city.value)
    }
    fun getCities()=profileRepository.getCities().handleResult(_cities)

    fun saveCities(cities:List<City>){
        val map= mutableMapOf<String,Int>()
        cities.map {
            map.put(it.mapCity.first,it.mapCity.second)
        }
        _citiesList.value=map
    }

}