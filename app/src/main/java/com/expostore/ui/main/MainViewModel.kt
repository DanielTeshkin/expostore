package com.expostore.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R

class MainViewModel : ViewModel() {
    lateinit var navController: NavController

    fun navigateToProfile(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_mainFragment_to_profileFragment)
    }
}