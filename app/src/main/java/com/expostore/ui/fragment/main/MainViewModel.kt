package com.expostore.ui.fragment.main

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.data.AppPreferences
import com.expostore.data.repositories.MainRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.db.enities.AdvertisingDao
import com.expostore.db.model.TokenModel
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.profile.ProfileModel
import com.expostore.model.profile.name
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.main.interactor.MainInteractor
import com.expostore.ui.state.MainState
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: MainInteractor,
    private val mainRepository: MainRepository,
    private  val profileRepository: ProfileRepository
) : BaseViewModel() {
    private val _uiState = MutableSharedFlow<MainState>()
    val uiState = _uiState.asSharedFlow()
    private val _profileModel = MutableStateFlow<ProfileModel>(ProfileModel())
    val profileModel = _profileModel.asStateFlow()
     val token=MutableStateFlow<TokenModel>(TokenModel())
    private val _selection=MutableSharedFlow<ResponseState<List<SelectionModel>>>()
            val selection=_selection.asSharedFlow()
    private val _profile=MutableSharedFlow<ResponseState<ProfileModel>>()
    val profile=_profile.asSharedFlow()
    private val _advertisingModel=MutableSharedFlow<ResponseState<List<CategoryAdvertisingModel>>>()
    val advertisingModel=_advertisingModel.asSharedFlow()
     val ad= MutableStateFlow<List<CategoryAdvertisingModel>>(listOf())

    override fun onStart() {

    }



    fun load() {


        interactor.loadAll().handleResult({ isLoading ->
                    emit(_uiState, MainState.Loading(isLoading))
                }, { data ->
                    when (data) {
                        is MainInteractor.MainData.Categories -> emit(
                            _uiState,
                            MainState.SuccessCategory(data.items)
                        )
                        is MainInteractor.MainData.Advertising -> emit(
                            _uiState,
                            MainState.SuccessAdvertising(data.items)
                        )
                        is MainInteractor.MainData.Profile -> emit(
                            _uiState,
                            MainState.SuccessProfile(data.item)
                        )
                    }
                }, {
                    emit(_uiState, MainState.Error(it))
                })


    }

    fun getSelections()=mainRepository.getSelections().handleResult(_selection)
    fun getProfile()=profileRepository.getProfile().handleResult(_profile)
    fun  getAdvertisingCategory(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mainRepository.getAdvertising().handleResult(_advertisingModel)
            }
        }

    }



   fun saveProfileInfo(model: ProfileModel) {
        _profileModel.value = model
   }

    fun navigateToProfileOrOpen(){
        when(token.value.access.isNullOrEmpty()){
            true->navigateToOpen()
            false->navigateToProfile()
        }
    }

    fun getToken(){
      viewModelScope.launch {
          withContext(Dispatchers.IO){
          val model = interactor.getToken()
          if (model != null) {
              token.value = model
          }
      }}
  }
    fun navigateToCreateProductOrOpen(){
        when(token.value.access.isNullOrEmpty()){
            true->navigateToOpen()
            false->navigateToCreateProduct() }
    }
    private fun navigateToCreateProduct() {
        navigationTo(MainFragmentDirections.actionMainFragmentToAddProductFragment())
    }

   private fun navigateToOpen() {
        navigationTo(MainFragmentDirections.actionMainFragmentToOpenFragment())
    }

  private  fun navigateToProfile() {
        navigationTo(MainFragmentDirections.actionMainFragmentToProfileFragment())
  }
    fun navigateToSelectionFragment(){
        navigationTo(MainFragmentDirections.actionMainFragmentToDetailCategoryFragment())
    }

    fun navigateToProduct(){
        navigationTo(MainFragmentDirections.actionMainFragmentToProductFragment())
    }
}
