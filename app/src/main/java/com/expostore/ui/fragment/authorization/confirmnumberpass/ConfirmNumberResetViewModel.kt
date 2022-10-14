package com.expostore.ui.fragment.authorization.confirmnumberpass

import android.util.Log
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.authorization.registration.confirmnumber.ConfirmNumberFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberResetViewModel @Inject constructor(private val repository: AuthorizationRepository) : BaseViewModel() {
    private val _enabledState= MutableStateFlow(false)
    val enabledState=_enabledState.asStateFlow()
    private val phone= MutableStateFlow("")
    private val _loading= MutableStateFlow(false)
    val loading=_loading.asStateFlow()

    fun confirmNumber() {
       repository.confirmNumberReset("7"+phone.value).handleResult( { _loading.value=it },{
           navigationTo(
               ConfirmNumberResetDirections.actionConfirmNumberPassToPasswordRecoveryFragment(
                   phone.value
               )
           )
       })

    }

    fun checkLengthNumber(number: String){
        if (number.length==10){
            updateEnabled(true)
            updatePhone(number)
        }
        else updateEnabled(false)
    }

    private fun updatePhone(number:String){
        phone.value=number
    }

    private fun updateEnabled(state:Boolean){
        _enabledState.value=state
    }
    fun navigateToWeb()=navigationTo(ConfirmNumberResetDirections.actionConfirmNumberPassToWebViewFragment(
        url = "https://expostore.ru/agreement.html", format = ""
    ))

    fun toBack() {
        navigationTo(ConfirmNumberFragmentDirections.actionNumberFragmentToOpenFragment())
    }
    override fun onStart() {
      Log.i("ffff","ggg")
    }
}