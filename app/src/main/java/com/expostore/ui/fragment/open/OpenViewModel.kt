package com.expostore.ui.fragment.open

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R

class OpenViewModel : ViewModel() {
    lateinit var navController: NavController

    //Навигация на "Войти в аккаунт"
    fun navigateToSignIn(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_openFragment_to_loginFragment)
    }

    //Навигация на "Зарегистрироваться"
    fun navigateToSignUp(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_openFragment_to_numberFragment)
    }
}