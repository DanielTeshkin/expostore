package com.expostore.ui.fragment.authorization.passwordrecovery

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.expostore.R
import com.expostore.data.remote.api.ApiWorker

import com.expostore.data.remote.api.ServerApi
import com.expostore.data.remote.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.authorization.confirmnumberpass.ConfirmNumberResetDirections
import com.expostore.ui.fragment.authorization.registration.confirmcode.ConfirmCodeFragmentDirections
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor( private val repository:AuthorizationRepository) : BaseViewModel() {


    private val text= MutableStateFlow("")
    val resetText=text.asStateFlow()
    private val code=MutableStateFlow("")
    private val _clickable= MutableStateFlow(false)
          val clickable=_clickable.asStateFlow()
   private val phone=MutableStateFlow("")

    private val _state= MutableSharedFlow<ResponseState<ConfirmCodeResponseData>>()
    var state=_state.asSharedFlow()

    fun saveNumber(number: String){
        phone.value=number
    }
    fun saveInput(input: String){
        code.value=input
    }

    fun confirmCode(){
        repository.confirmCodeReset(phone.value,code.value).handleResult(_state,{
            navigationTo(PasswordRecoveryFragmentDirections.actionPasswordRecoveryFragmentToNewPasswordFragment(phone.value))
        })
    }

    fun confirmNumber(){
        repository.confirmNumberReset(phone.value).handleResult()

    }
    public override fun back(){
        navigationTo(ConfirmCodeFragmentDirections.actionConfirmNumberFragmentToNumberFragment())
    }
    fun timerStart(){
        timer.start()
    }

   private val timer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val time=millisUntilFinished/1000
           text.value = "Отправить код повторно (доступно через $time сек.)"
            _clickable.value = false
        }

        override fun onFinish() {
            text.value ="Отправить код"
            _clickable.value = true
        }
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }


}