package com.expostore.ui.fragment.main.interactor

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.data.repositories.MainRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.db.LocalWorker
import com.expostore.db.enities.selection.toModel
import com.expostore.db.enities.toDao
import com.expostore.db.enities.toModel
import com.expostore.model.category.CategoryAdvertisingModel

import com.expostore.model.category.SelectionModel
import com.expostore.model.category.toModel
import com.expostore.model.profile.ProfileModel

import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MainInteractor @Inject constructor(
                                         private val localWorker: LocalWorker,
                                         private val mainRepository: MainRepository,
                                           private val profileRepository: ProfileRepository)  {
    



    fun loadAll()=flow{
        mainRepository.getSelections().collect {
            emit(MainData.Categories(it))
        }
        mainRepository.getAdvertising().collect {
            emit(MainData.Advertising(it))
        }

            emit(MainData.Profile(profileRepository.getProfileRemote()))




    }



    //fun loadAdvertising()= flow {
       // mainRepository.getAdvertising().collect {
         //   emit(MainData.Advertising(it))
       // }
   // }
   private suspend fun getProfile(): ProfileModel =profileRepository.getProfileRemote()
       fun getToken()= localWorker.getToken()

   suspend fun saveProfile(result:ProfileModel) = localWorker.saveProfile(result.toDao)



    fun getSelection()=mainRepository.getSelections()
   // fun getAdvertisingCategory()=mainRepository.getAdvertising()
     fun getMyProfile()=profileRepository.getProfile()









    sealed class MainData {
        data class Categories(val items: List<SelectionModel>) : MainData()
        data class Advertising(val items: List<CategoryAdvertisingModel>) : MainData()
        data class Profile(val item:ProfileModel):MainData()
    }
}