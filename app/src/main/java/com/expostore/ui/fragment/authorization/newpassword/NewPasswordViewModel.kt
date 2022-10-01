package com.expostore.ui.fragment.authorization.newpassword

import com.expostore.data.remote.api.pojo.signup.ResetPasswordRequest
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.vms.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewPasswordViewModel @Inject constructor(private val authorizationRepository: AuthorizationRepository) : BaseViewModel() {
    override fun onStart() {
        TODO("Not yet implemented")
    }
     fun reset(phone:String,password1:String,password2: String)= authorizationRepository.newPassword(
         ResetPasswordRequest(phone,password1, password2)
     ).handleResult({

     },{
         navigationTo(NewPasswordFragmentDirections.actionNewPasswordFragmentToLoginFragment())
     })

}