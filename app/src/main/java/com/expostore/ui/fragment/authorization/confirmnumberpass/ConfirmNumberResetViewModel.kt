package com.expostore.ui.fragment.authorization.confirmnumberpass

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

    fun confirmNumber() {
       repository.confirmNumberReset("7"+phone.value).handleResult( {
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

    fun toBack() {
        navigationTo(ConfirmNumberFragmentDirections.actionNumberFragmentToOpenFragment())
    }
    override fun onStart() {
        TODO("Not yet implemented")
    }
}