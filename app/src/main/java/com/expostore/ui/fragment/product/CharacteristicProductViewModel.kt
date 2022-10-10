package com.expostore.ui.fragment.product

import android.util.Log
import com.expostore.ui.base.vms.BaseViewModel

class CharacteristicProductViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel
    override fun onStart() {
       Log.i("ff","")
    }

    fun navigateBack()=navController.popBackStack()
}