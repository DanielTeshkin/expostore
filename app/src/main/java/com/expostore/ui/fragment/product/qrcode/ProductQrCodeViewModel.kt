package com.expostore.ui.fragment.product.qrcode

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.ui.base.vms.BaseViewModel

class ProductQrCodeViewModel : BaseViewModel() {


    fun navigateBack() = navController.popBackStack()
    override fun onStart() {
        Log.i("start", "check")
    }
}