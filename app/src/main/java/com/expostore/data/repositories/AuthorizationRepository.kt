package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.db.LocalWorker
import com.expostore.db.model.TokenModel
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

    suspend fun saveToken( refresh:String,access:String) = localWorker.saveToken(TokenModel(refresh,access))
}