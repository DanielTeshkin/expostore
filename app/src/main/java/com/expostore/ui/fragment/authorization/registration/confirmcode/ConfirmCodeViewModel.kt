package com.expostore.ui.fragment.authorization.registration.confirmcode

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.authorization.login.LoginFragmentDirections
import com.expostore.ui.fragment.authorization.registration.completion.CompletionFragmentDirections
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
class ConfirmCodeViewModel @Inject constructor(private val registration: InteractorRegistration) : BaseViewModel() {
    private val _state=MutableSharedFlow<ResponseState<ConfirmCodeResponseData>>()
       var state=_state.asSharedFlow()


    fun confirmCode(phone:String,code:String){
        registration.confirmCode(phone,code).handleResult(_state,{
            navigationTo(ConfirmCodeFragmentDirections.actionConfirmNumberFragmentToCreatePasswordFragment(phone))
        })

    }
    fun back(){
        navigationTo(ConfirmCodeFragmentDirections.actionConfirmNumberFragmentToNumberFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}





