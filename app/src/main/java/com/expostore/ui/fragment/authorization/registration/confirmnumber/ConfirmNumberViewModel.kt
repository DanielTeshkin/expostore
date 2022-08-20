package com.expostore.ui.fragment.authorization.registration.confirmnumber

import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

const val MAX_LENGTH = 12
const val PHONE_INPUT = 11
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class ConfirmNumberViewModel@Inject constructor(private val registration: AuthorizationRepository) : BaseViewModel() {

    private val _enabledState=MutableStateFlow(false)
    val enabledState=_enabledState.asStateFlow()
    private val phone= MutableStateFlow("")

    fun confirmNumber() {
        registration.confirmNumber("7"+phone.value).handleResult( {
                navigationTo(
                    ConfirmNumberFragmentDirections.actionNumberFragmentToConfirmNumberFragment(
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

    fun updateEnabled(state:Boolean){
        _enabledState.value=state
    }

    fun toBack() {
        navigationTo(ConfirmNumberFragmentDirections.actionNumberFragmentToOpenFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

}




