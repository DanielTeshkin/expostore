package com.expostore.ui.fragment.authorization.registration.createpassword

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.data.AppPreferences
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.authorization.registration.confirmcode.ConfirmCodeFragmentDirections
import com.expostore.ui.fragment.authorization.registration.interactor.InteractorRegistration
import com.expostore.ui.state.ResponseState
import com.expostore.utils.hideKeyboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class CreatePasswordViewModel @Inject constructor(private val registration: InteractorRegistration) : BaseViewModel() {
     private val _ui= MutableSharedFlow<ResponseState<SignUpResponseData>>()
    val ui=_ui.asSharedFlow()
    private val _instance =MutableLiveData<Boolean>()
    val instance=_instance

    fun signUp(phone: String, password: String) {
        registration.createPassword(phone,password).handleResult(_ui,{
            navigationTo(CreatePasswordFragmentDirections.actionCreatePasswordFragmentToCompletionFragment())
        })
    }
    fun checkPassword(first:String,second:String){
        if(first.isNotEmpty()){
            val check= first == second
            _instance.value=check
        }

    }
    fun backEnd(){
        navigationTo(CreatePasswordFragmentDirections.actionCreatePasswordFragmentToConfirmNumberFragment())
    }


    override fun onStart() {
        TODO("Not yet implemented")
    }
}