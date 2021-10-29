package com.expostore.ui.product.qrcode

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R

class ProductQrCodeViewModel : ViewModel() {

    lateinit var navController: NavController

    fun navigateBack(view: View){
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }
}