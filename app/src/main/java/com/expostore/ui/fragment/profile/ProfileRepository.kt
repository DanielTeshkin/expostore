package com.expostore.ui.fragment.profile

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.profile.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepository@Inject constructor( private val apiWorker: ApiWorker):BaseInteractor() {

    fun getProfile() = flow{
        val result=handleOrDefault(GetProfileResponseData()){apiWorker.getProfile()}
        emit(result.toModel)
    }
    fun editProfile( editProfileRequestData: EditProfileRequestData)= flow {
        val result=handleOrDefault(EditProfileResponseData()){apiWorker.editProfile(editProfileRequestData)}
        emit(result)
    }

}