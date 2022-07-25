package com.expostore.ui.fragment.authorization.registration.completion




import com.expostore.api.ServerApi
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getcities.City
import com.expostore.data.AppPreferences
import com.expostore.data.repositories.IdentificationRepository
import com.expostore.ui.base.BaseViewModel

import com.expostore.ui.state.ResponseState
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class CompletionViewModel@Inject constructor(private val registration: IdentificationRepository): BaseViewModel() {

    private val _ui= MutableSharedFlow<ResponseState<EditProfileResponseData>>()
    val ui=_ui.asSharedFlow()

    override fun onStart() {
        TODO("Not yet implemented")
    }
          fun editProfile(editProfileRequestData: EditProfileRequestData){
              registration.editProfile(editProfileRequestData).handleResult(_ui) {
                  navigationTo(CompletionFragmentDirections.actionCompletionFragmentToMainFragment())
              }
          }
    fun backEnd(){
        navigationTo(CompletionFragmentDirections.actionCompletionFragmentToCreatePasswordFragment())
    }




}