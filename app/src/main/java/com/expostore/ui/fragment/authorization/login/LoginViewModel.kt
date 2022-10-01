package com.expostore.ui.fragment.authorization.login

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope

import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.model.auth.SignInResponseDataModel
import com.expostore.ui.base.vms.BaseViewModel

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authorization: AuthorizationRepository
) : BaseViewModel() {

    private val _uiState = MutableSharedFlow<ResponseState<SignInResponseDataModel>>()
    val uiState = _uiState.asSharedFlow()
    private val phone= MutableStateFlow("")
    private val password=MutableStateFlow("")
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()


    override fun onStart() {

    }

   fun updatePhone(input:String){
            phone.value=input
       checkFields()
    }
   fun updatePassword(input: String){
       password.value=input
       checkFields()
   }
    private fun checkFields(){
        _enabled.value = password.value.isNotEmpty() and phone.value.isNotEmpty() and (phone.value.length==11)
    }


    fun signIn() {
       authorization.login(phone.value, password.value)
            .handleResult(_uiState, {
                saveToken(it.refresh,it.access)
                navigationTo(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            })
    }

   private fun saveToken(refresh:String, access:String) =
       viewModelScope.launch {
           authorization.saveToken(refresh, access)
       }
    fun navigateToReset()=navController.navigate(R.id.action_loginFragment_to_confirmNumberPass)
fun navigateToBack()= navController.popBackStack()

    fun navigateBack(view: View) {
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }
}