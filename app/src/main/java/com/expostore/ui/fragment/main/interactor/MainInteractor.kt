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
import com.expostore.model.profile.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class MainInteractor @Inject constructor(private val apiWorker: ApiWorker,
                                         private val localWorker: LocalWorker,
                                         private val mainRepository: MainRepository,
                                           private val profileRepository: ProfileRepository) : BaseInteractor() {
    
    private suspend fun getCategories() = handleOrEmptyList { apiWorker.getCategories() }.map { it.toModel }

    private suspend fun getAdvertising() =
        handleOrEmptyList { apiWorker.getCategoryAdvertising() }.map { it.toModel }

    fun load() = flow {
        emit(MainData.Categories(getCategories()))
        emit(MainData.Advertising(getAdvertising()))
       emit(MainData.Profile(getProfile()))
    }
    fun loadAll()=flow{
        mainRepository.getSelections().collect {
            emit(MainData.Categories(it))
        }
        mainRepository.getAdvertising().collect {
            emit(MainData.Advertising(it))
        }

            emit(MainData.Profile(getProfile()))

    }


    fun loadSelection()=flow{
             mainRepository.getSelections().collect {
                 emit(MainData.Categories(it))
             }
    }

    fun loadProfile()=flow {
        profileRepository.getProfile().collect {
            emit(MainData.Profile(it))
        }
    }
    //fun loadAdvertising()= flow {
       // mainRepository.getAdvertising().collect {
         //   emit(MainData.Advertising(it))
       // }
   // }
   private suspend fun getProfile(): ProfileModel {
       val result=handleOrDefault(GetProfileResponseData()){apiWorker.getProfile()}.toModel
       return result
   }
       fun getToken()= localWorker.getToken()

    fun saveProfile(result:ProfileModel) = flow {
       val result= localWorker.saveProfile(result.toDao)
        emit(result)
    }

    fun getSelection()=mainRepository.getSelections()
   // fun getAdvertisingCategory()=mainRepository.getAdvertising()
     fun getMyProfile()=profileRepository.getProfile()









    sealed class MainData {
        data class Categories(val items: List<SelectionModel>) : MainData()
        data class Advertising(val items: List<CategoryAdvertisingModel>) : MainData()
        data class Profile(val item:ProfileModel):MainData()
    }
}