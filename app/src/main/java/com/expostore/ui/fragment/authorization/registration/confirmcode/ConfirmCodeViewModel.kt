package com.expostore.ui.fragment.authorization.registration.confirmcode




import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.data.repositories.AuthorizationRepository

import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class ConfirmCodeViewModel @Inject constructor(private val registration: AuthorizationRepository) : BaseViewModel() {
    private val _state=MutableSharedFlow<ResponseState<ConfirmCodeResponseData>>()
       var state=_state.asSharedFlow()


    fun confirmCode(phone:String,code:String){
        registration.confirmCode(phone,code).handleResult(_state,{
            navigationTo(ConfirmCodeFragmentDirections.actionConfirmNumberFragmentToCreatePasswordFragment(phone))
        })

    }
   public override fun back(){
       navigationTo(ConfirmCodeFragmentDirections.actionConfirmNumberFragmentToNumberFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}





