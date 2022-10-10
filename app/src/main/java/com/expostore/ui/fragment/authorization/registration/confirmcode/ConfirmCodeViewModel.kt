package com.expostore.ui.fragment.authorization.registration.confirmcode




import android.os.CountDownTimer
import com.expostore.data.remote.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.data.repositories.AuthorizationRepository

import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class ConfirmCodeViewModel @Inject constructor(private val registration: AuthorizationRepository) : BaseViewModel() {
    private val text= MutableStateFlow("")
    val resetText=text.asStateFlow()
    private val code= MutableStateFlow("")
    private val _clickable= MutableStateFlow(false)
    val clickable=_clickable.asStateFlow()
    private val phone= MutableStateFlow("")
    private val _state=MutableSharedFlow<ResponseState<ConfirmCodeResponseData>>()
    val state=_state.asSharedFlow()
    private val _loading= MutableStateFlow(false)
    val loading=_loading.asStateFlow()
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()


    fun confirmCode(){
        _enabled.value=false
        registration.confirmCode(phone.value,code.value).handleResult({_loading.value=it},{
            navigationTo(ConfirmCodeFragmentDirections.actionConfirmNumberFragmentToCreatePasswordFragment(phone.value))
        },{
            _enabled.value=true
        })
    }
    fun saveInput(input:String){
        code.value=input
        _enabled.value= input.length == 6

    }

    fun saveNumber(number:String){
        phone.value=number
    }
    fun timerStart(){
        timer.start()
    }
    fun confirmNumber()=registration.confirmNumber(phone.value).handleResult()
   public override fun back(){
       navigationTo(ConfirmCodeFragmentDirections.actionConfirmNumberFragmentToNumberFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
  private  val timer = object : CountDownTimer(60000, 1000) {
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
}





