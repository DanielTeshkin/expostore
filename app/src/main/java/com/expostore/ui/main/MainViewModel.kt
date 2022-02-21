package com.expostore.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    lateinit var navController: NavController

    fun navigateToProfile(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_mainFragment_to_profileFragment)
    }

    fun navigateToAddProduct(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_mainFragment_to_addProductFragment)
    }
}