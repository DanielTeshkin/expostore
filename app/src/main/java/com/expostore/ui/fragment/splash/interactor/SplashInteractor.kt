package com.expostore.ui.fragment.splash.interactor

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.profile.ProfileModel
import com.expostore.model.profile.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class SplashInteractor @Inject constructor(private val apiWorker: ApiWorker) : BaseInteractor() {
    fun fetchUser() = flow {
        val response = handleOrDefault(GetProfileResponseData()) { apiWorker.getProfile() }
        emit(response.toModel)
    }
}