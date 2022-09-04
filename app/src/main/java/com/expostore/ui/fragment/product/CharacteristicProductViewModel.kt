package com.expostore.ui.fragment.product

import androidx.lifecycle.ViewModel
import com.expostore.ui.base.BaseViewModel

class CharacteristicProductViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun navigateBack()=navController.popBackStack()
}