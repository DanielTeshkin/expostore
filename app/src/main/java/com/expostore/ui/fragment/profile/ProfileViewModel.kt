package com.expostore.ui.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R

class ProfileViewModel : ViewModel() {

    lateinit var navController: NavController

    fun navigateToMyProducts(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_profileFragment_to_myProductsFragment)
    }
}