package com.expostore.ui.fragment.splash

import android.content.Context
import com.expostore.R
import com.expostore.data.AppPreferences
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.splash.interactor.SplashInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val interactor: SplashInteractor,
    private val context: Context
) : BaseViewModel() {


    override fun onStart() {
        interactor.fetchUser().handleResult(onSuccess =  {
            navController.navigate(R.id.action_splashFragment_to_mainFragment)
        }, onError =  {
            if (AppPreferences.getSharedPreferences(context).getString("token", "")
                    .isNullOrEmpty()
            ) {
                navController.navigate(R.id.action_splashFragment_to_loginFragment)
            }
        })
    }
}