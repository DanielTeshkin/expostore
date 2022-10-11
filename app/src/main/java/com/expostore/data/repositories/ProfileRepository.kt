package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.getcities.toModel
import com.expostore.data.remote.api.pojo.getprofile.EditProfileRequest
import com.expostore.data.remote.api.pojo.getprofile.GetProfileResponseData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.response.EditResponseProfile
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.local.db.enities.toDao
import com.expostore.model.profile.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker) :BaseRepository() {


    fun getProfile()= singleOperator(
        databaseQuery = {localWorker.getProfile() },
        mapper = {it.toModel},
        networkCall = { handleOrDefault(GetProfileResponseData()){apiWorker.getProfile()}.toModel},
        saveCallResult = { localWorker.saveProfile(it.toDao) }
    )
   fun getProfileRemote() =flow {
       val result= handleOrDefault(GetProfileResponseData()) { apiWorker.getProfile() }.toModel
       emit(result)
    }

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

    fun getToken()= localWorker.getToken()

    suspend fun deleteAll() {
        localWorker.removeToken()
     localWorker.deleteProfile()
        localWorker.removeChats()
    }

}