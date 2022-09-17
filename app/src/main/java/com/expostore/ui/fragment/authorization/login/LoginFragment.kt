package com.expostore.ui.fragment.authorization.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.R
import com.expostore.databinding.LoginFragmentBinding
import com.expostore.model.auth.SignInResponseDataModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    override var isBottomNavViewVisible = false

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignInNext.click {
            loginViewModel.signIn()
        }

        binding.btnForgotPassword.click {
            Log.i("dddd","aaaa")
          loginViewModel.navigateToReset()
        }
        binding.imageButton.click {

            loginViewModel.navigateToBack()
        }
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.apply {
            start(findNavController())
            subscribe(navigation) { navigateSafety(it) }
            subscribe(uiState) { handleState(it) }
        }
        binding.apply {
            etNumber.addTextChangedListener { loginViewModel.updatePhone(it.toString()) }
            etPassword.addTextChangedListener { loginViewModel.updatePassword(it.toString()) }
            state {
                loginViewModel.enabled.collect {
                    btnSignInNext.isEnabled=it
                }
            }
        }
    }




    }
