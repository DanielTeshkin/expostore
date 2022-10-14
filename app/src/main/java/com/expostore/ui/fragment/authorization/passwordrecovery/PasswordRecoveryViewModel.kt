package com.expostore.ui.fragment.authorization.passwordrecovery

import android.os.CountDownTimer
import android.util.Log

import com.expostore.data.remote.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.vms.BaseViewModel
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
    private val _loading= MutableStateFlow(false)
    val loading=_loading.asStateFlow()
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()

    fun saveNumber(number: String){
        phone.value=number
    }
    fun saveInput(input: String){
        code.value=input
        _enabled.value= input.length == 6
    }

    fun confirmCode(){
        _enabled.value=false
        repository.confirmCodeReset(phone.value,code.value).handleResult({_loading.value=it},{
            navigationTo(PasswordRecoveryFragmentDirections.actionPasswordRecoveryFragmentToNewPasswordFragment(phone.value))
        },{
            _enabled.value=true
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
        Log.i("ccc","vvv")
    }


}