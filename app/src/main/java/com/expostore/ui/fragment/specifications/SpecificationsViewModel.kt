package com.expostore.ui.fragment.specifications

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation

class SpecificationsViewModel : ViewModel() {

    lateinit var navController: NavController
    lateinit var categories: ArrayList<String>

    fun saveCategories(view:View){
        navController = Navigation.findNavController(view)
        navController.previousBackStackEntry?.savedStateHandle?.set("specifications", categories)
        navController.popBackStack()
    }
}