package com.expostore.ui.fragment.authorization.registration.interactor

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
/**
 * @author Teshkin Daniel
 */
class InteractorRegistration @Inject constructor(private val apiWorker: ApiWorker):BaseInteractor() {

    fun confirmNumber(phone:String)= flow{
        val result=handleOrDefault(ConfirmNumberResponseData()){apiWorker.confirmNumber(ConfirmNumberRequestData(phone)) }
        emit(result)
    }
    fun confirmCode(phone: String,code:String) = flow {
        val result=handleOrDefault(ConfirmCodeResponseData()){apiWorker.confirmCode(
            ConfirmCodeRequestData(phone, code)
        )}
        emit(result)
    }
    fun createPassword(username: String,password:String)= flow{
        val result=handleOrDefault(SignUpResponseData()){apiWorker.registration(SignUpRequestData(username, password))}
        emit(result)
    }
    fun editProfile( editProfileRequestData: EditProfileRequestData)= flow {
        val result=handleOrDefault(EditProfileResponseData()){apiWorker.editProfile(editProfileRequestData)}
        emit(result)
    }


}