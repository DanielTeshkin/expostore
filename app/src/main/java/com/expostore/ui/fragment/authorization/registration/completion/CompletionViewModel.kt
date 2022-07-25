package com.expostore.ui.fragment.authorization.registration.completion




import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.BaseViewModel

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class CompletionViewModel@Inject constructor(private val registration: AuthorizationRepository): BaseViewModel() {

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