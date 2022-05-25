package com.expostore.ui.fragment.profile

import android.content.Context
import com.expostore.api.ApiWorker
import com.expostore.api.pojo.addshop.AddShopRequestData
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getcities.toModel
import com.expostore.api.pojo.getprofile.EditProfileRequest
import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.api.response.EditResponseProfile
import com.expostore.model.profile.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepository@Inject constructor( private val apiWorker: ApiWorker):BaseInteractor() {

    fun getProfile() = flow{
        val result=handleOrDefault(GetProfileResponseData()){apiWorker.getProfile()}
        emit(result.toModel)
    }

    fun patchProfile( editProfileRequest: EditProfileRequest)= flow {
        val result=handleOrDefault(EditResponseProfile()){apiWorker.patchProfile(editProfileRequest)}
        emit(result)
    }

    fun getCities() = flow{
        val result = handleOrEmptyList { apiWorker.getCities() }.map { it.toModel }
        emit(result)
    }
    fun saveImage(saveImageRequestData: SaveImageRequestData)=
        flow{
            val result=handleOrDefault(SaveImageResponseData()){apiWorker.saveImage(saveImageRequestData)}
            emit(result)
        }

}