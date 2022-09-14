package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.data.remote.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.data.remote.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.data.remote.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.data.remote.api.pojo.editprofile.EditProfileRequestData
import com.expostore.data.remote.api.pojo.editprofile.EditProfileResponseData
import com.expostore.data.remote.api.pojo.signin.SignInResponseData
import com.expostore.data.remote.api.pojo.signup.SignUpRequestData
import com.expostore.data.remote.api.pojo.signup.SignUpResponseData
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.local.db.model.TokenModel
import com.expostore.model.auth.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker):BaseRepository() {

    fun confirmNumber(phone:String)= flow{
        val result=handleOrDefault(ConfirmNumberResponseData()){apiWorker.confirmNumber(
            ConfirmNumberRequestData(phone)
        ) }
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

    fun login(username: String, password: String) = flow {
        val result =
            handleOrDefault(SignInResponseData()) { apiWorker.authorization(username, password) }
        emit(result.toModel)
    }

    fun confirmNumberReset(phone:String)= flow{
        val result=handleOrDefault(ConfirmNumberResponseData()){apiWorker.confirmPassNumber(
            ConfirmNumberRequestData(phone)
        ) }
        emit(result)
    }
    fun confirmCodeReset(phone: String,code:String) = flow {
        val result=handleOrDefault(ConfirmCodeResponseData()){apiWorker.confirmPassCode(
            ConfirmCodeRequestData(phone, code)
        )}
        emit(result)
    }

    fun newPassword(request: SignUpRequestData) = flow{
        emit(handleOrDefault(ConfirmCodeResponseData()){apiWorker.resetPassword(request)})
    }

   fun saveToken( refresh:String,access:String) = localWorker.saveToken(TokenModel(refresh,access))
    fun getToken()=localWorker.getToken()
}