package com.expostore.ui.fragment.authorization.login.interactor

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.model.auth.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class LoginInteractor @Inject constructor(private val apiWorker: ApiWorker) : BaseInteractor() {
    fun login(username: String, password: String) = flow {
        val result =
            handleOrDefault(SignInResponseData()) { apiWorker.authorization(username, password) }
        emit(result.toModel)
    }
}