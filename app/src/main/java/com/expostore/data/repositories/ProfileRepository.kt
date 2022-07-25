package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getcities.toModel
import com.expostore.api.pojo.getprofile.EditProfileRequest
import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.api.response.EditResponseProfile
import com.expostore.db.LocalWorker
import com.expostore.db.enities.toDao
import com.expostore.model.profile.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker) :BaseRepository() {


    fun getProfile()= operator(
        databaseQuery = {localWorker.getProfile().toModel},
        networkCall = { handleOrDefault(GetProfileResponseData()){apiWorker.getProfile()}.toModel},
        saveCallResult = { localWorker.saveProfile(it.toDao) }
    )

    fun patchProfile( editProfileRequest: EditProfileRequest)= flow {
        val result=handleOrDefault(EditResponseProfile()){apiWorker.patchProfile(editProfileRequest)}
        emit(result)
    }

    fun getCities() = flow{
        val result = handleOrEmptyList { apiWorker.getCities() }.map { it.toModel }
        emit(result)
    }
    fun saveImage(saveImageRequestData: List<SaveImageRequestData>)=
        flow{
            val result=handleOrDefault(SaveImageResponseData()){apiWorker.saveImage(saveImageRequestData)}
            emit(result)
        }

    suspend fun getToken()= localWorker.getToken()

    suspend fun deleteAll() {
        localWorker.removeToken()
     localWorker.deleteProfile()
        localWorker.removeChats()
    }

}