package com.expostore.ui.open

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R

class OpenViewModel : ViewModel() {
    lateinit var navController: NavController

    fun navigateToSignIn(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_openFragment_to_loginFragment)
    }
}