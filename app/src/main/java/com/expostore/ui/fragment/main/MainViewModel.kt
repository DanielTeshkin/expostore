package com.expostore.ui.fragment.main

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.main.interactor.MainInteractor
import com.expostore.ui.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: MainInteractor
) : BaseViewModel() {
    private val _uiState = MutableSharedFlow<MainState>()
    val uiState = _uiState.asSharedFlow()

    override fun onStart() {
       }
    fun load() {
        interactor.load().handleResult({ isLoading ->
            emit(_uiState, MainState.Loading(isLoading))
        },{ data ->
            when (data) {
                is MainInteractor.MainData.Categories -> emit(
                    _uiState,
                    MainState.SuccessCategory(data.items)
                )
                is MainInteractor.MainData.Advertising -> emit(
                    _uiState,
                    MainState.SuccessAdvertising(data.items)
                )
                is MainInteractor.MainData.Profile-> emit(_uiState,
                    MainState.SuccessProfile(data.item)
                )
            }
        }, {
            emit(_uiState, MainState.Error(it))
        }) }

    fun navigateToCreateProduct(){
        navigationTo(MainFragmentDirections.actionMainFragmentToAddProductFragment())
    }
}