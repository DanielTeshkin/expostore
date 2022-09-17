package com.expostore.ui.fragment.product

import android.util.Log
import androidx.lifecycle.ViewModel
import com.expostore.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


class PriceHistoryViewModel : BaseViewModel() {

    override fun onStart() {
        Log.i("abc","ddd")
    }
    fun navigate()=navController.popBackStack()
}