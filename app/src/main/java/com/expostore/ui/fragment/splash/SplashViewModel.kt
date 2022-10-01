package com.expostore.ui.fragment.splash

import com.expostore.R
import com.expostore.ui.base.vms.BaseViewModel


class SplashViewModel (

) : BaseViewModel() {

    override fun onStart() {
        navController.navigate(R.id.action_splashFragment_to_mainFragment)
    }
}