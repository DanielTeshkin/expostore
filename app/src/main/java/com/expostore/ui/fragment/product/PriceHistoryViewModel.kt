package com.expostore.ui.fragment.product

import android.util.Log
import com.expostore.ui.base.vms.BaseViewModel


class PriceHistoryViewModel : BaseViewModel() {

    override fun onStart() {
        Log.i("abc","ddd")
    }
    fun navigate()=navController.popBackStack()
}