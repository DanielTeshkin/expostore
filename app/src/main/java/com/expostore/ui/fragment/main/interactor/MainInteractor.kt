package com.expostore.ui.fragment.main.interactor

import com.expostore.data.repositories.MainRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.model.category.CategoryAdvertisingModel

import com.expostore.model.category.SelectionModel
import com.expostore.model.profile.ProfileModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class MainInteractor @Inject constructor(private val mainRepository: MainRepository,
                                           private val profileRepository: ProfileRepository) {
    



    fun loadAll()=flow{
        mainRepository.getSelections().collect {
            emit(MainData.Categories(it))
        }
        mainRepository.getAdvertising().collect {
            emit(MainData.Advertising(it))
        }
               getProfile().collect {
                   emit(MainData.Profile(it))
               }

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

       fun getToken()= profileRepository.getToken()

    fun getProfile()=profileRepository.getProfile()

    fun getSelection()=mainRepository.getSelections()
   // fun getAdvertisingCategory()=mainRepository.getAdvertising()










    sealed class MainData {
        data class Categories(val items: List<SelectionModel>) : MainData()
        data class Advertising(val items: List<CategoryAdvertisingModel>) : MainData()
        data class Profile(val item:ProfileModel):MainData()
    }
}