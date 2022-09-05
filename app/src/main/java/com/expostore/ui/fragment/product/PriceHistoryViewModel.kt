package com.expostore.ui.fragment.product

import androidx.lifecycle.ViewModel
import com.expostore.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


class PriceHistoryViewModel : BaseViewModel() {

    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun navigate()=navController.popBackStack()
}