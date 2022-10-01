package com.expostore.ui.fragment.authorization.registration.createpassword


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.expostore.data.remote.api.pojo.signup.SignUpResponseData
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class CreatePasswordViewModel @Inject constructor(private val registration: AuthorizationRepository) : BaseViewModel() {
     private val _ui= MutableSharedFlow<ResponseState<SignUpResponseData>>()
    val ui=_ui.asSharedFlow()
    private val _instance =MutableLiveData<Boolean>()
    val instance=_instance

    fun signUp(phone: String, password: String) {
        registration.createPassword(phone,password).handleResult(_ui,{
            saveToken(it.refresh?:"",it.access?:"")
            navigationTo(CreatePasswordFragmentDirections.actionCreatePasswordFragmentToCompletionFragment())
        })
    }
    fun checkPassword(first:String,second:String){
        if(first.isNotEmpty()){
            val check= first == second
            _instance.value=check
        }

    }
    private fun saveToken(refresh:String, access:String) =
        viewModelScope.launch {
          registration.saveToken(refresh, access)
        }
    fun backEnd(){
        navigationTo(CreatePasswordFragmentDirections.actionCreatePasswordFragmentToConfirmNumberFragment())
    }


    override fun onStart() {
        TODO("Not yet implemented")
    }
}