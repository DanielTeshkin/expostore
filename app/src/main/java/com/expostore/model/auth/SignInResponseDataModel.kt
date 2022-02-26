package com.expostore.model.auth

import com.expostore.api.pojo.signin.SignInResponseData

/**
 * @author Fedotov Yakov
 */
data class SignInResponseDataModel(
    val refresh: String = "",
    val access: String = ""
)

val SignInResponseData.toModel: SignInResponseDataModel
    get() = SignInResponseDataModel(
        refresh ?: "",
        access ?: ""
    )