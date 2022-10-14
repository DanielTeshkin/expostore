package com.expostore.ui.fragment.authorization.newpassword

import android.util.Log
import com.expostore.data.remote.api.pojo.signup.ResetPasswordRequest
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.vms.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewPasswordViewModel @Inject constructor(private val authorizationRepository: AuthorizationRepository) : BaseViewModel() {
    private val _loading= MutableStateFlow(false)
    val loading=_loading.asStateFlow()
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
   private val password1= MutableStateFlow("")
   private val password2=MutableStateFlow("")
    override fun onStart() {
      Log.i("ff","fg")
    }
    fun changePassword1(text:String){
        password1.value=text
        checkState()

    }
    fun changePassword2(text:String){
        password2.value=text
        checkState()
    }
    fun checkState(){
        _enabled.value= (password1.value.isNotEmpty() &&password2.value.isNotEmpty())
    }
     fun reset(phone:String)= authorizationRepository.newPassword(
         ResetPasswordRequest("7$phone",password1.value, password2.value)
     ).handleResult({
         _enabled.value!=it
           _loading.value=it
     },{
         navigationTo(NewPasswordFragmentDirections.actionNewPasswordFragmentToLoginFragment())
     })

}