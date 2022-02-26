package com.expostore.ui.fragment.main

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.model.category.CategoryModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.main.interactor.MainInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: MainInteractor
) : BaseViewModel() {
    lateinit var navController: NavController

    private val _uiState = MutableSharedFlow<ResponseState<List<CategoryModel>>>()
    val uiState = _uiState.asSharedFlow()

    override fun onStart() {
        interactor.geCategories().handleResult(_uiState)
    }

    fun navigateToProfile(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_mainFragment_to_profileFragment)
    }

    fun navigateToAddProduct(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_mainFragment_to_addProductFragment)
    }
}