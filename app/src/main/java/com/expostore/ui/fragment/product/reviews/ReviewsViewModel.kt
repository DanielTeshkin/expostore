package com.expostore.ui.fragment.product.reviews

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation

class ReviewsViewModel : ViewModel() {

    lateinit var navController: NavController

    fun navigateBack(view: View){
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }
}