package com.expostore.ui.fragment.main.interactor

import com.expostore.data.repositories.MainRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.local.db.enities.toDao
import com.expostore.data.repositories.SelectionRepository
import com.expostore.model.category.CategoryAdvertisingModel

import com.expostore.model.category.SelectionModel
import com.expostore.model.profile.ProfileModel

import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MainInteractor @Inject constructor(private val mainRepository: MainRepository,
                                         private val selectionRepository: SelectionRepository,
                                           private val profileRepository: ProfileRepository)  {
    
    fun loadAll()=flow{
        selectionRepository.getSelections().collect {
            emit(MainData.Categories(it))
        }
        mainRepository.getAdvertising().collect {
            emit(MainData.Advertising(it))
        }
         if(!profileRepository.getToken().isNullOrEmpty())
        profileRepository.getProfile().collect {
           emit(MainData.Profile(it))
        }
    }


       fun getToken()= profileRepository.getToken()
    sealed class MainData {
        data class Categories(val items: List<SelectionModel>) : MainData()
        data class Advertising(val items: List<CategoryAdvertisingModel>) : MainData()
        data class Profile(val item:ProfileModel):MainData()
    }
}