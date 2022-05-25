package com.expostore.ui.fragment.authorization.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.expostore.R
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.LoginFragmentBinding
import com.expostore.model.auth.SignInResponseDataModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    override var isBottomNavViewVisible = false

    private val loginViewModel: LoginViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          val saveToken :Show<SignInResponseDataModel> = { handleItem(it)}
        loginViewModel.apply {
            subscribe(navigation) { navigateSafety(it) }
            subscribe(uiState) { handleState(it,saveToken) }
        }

        binding.btnSignInNext.setOnClickListener {
            loginViewModel.signIn(
                binding.etNumber.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.btnForgotPassword.setOnClickListener {
            loginViewModel.confirmNumber(it, binding.etNumber.text.toString())
        }

        loginViewModel.context = requireContext()

        // Передача параметров во вьюмодель
        loginViewModel.btnSignIn = binding.btnSignInNext
        loginViewModel.btnForgotPassword = binding.btnForgotPassword
        // Константа длины номера
        loginViewModel.phoneLength = resources.getInteger(R.integer.phone_length)

        // Подключение наблюдателя текста
        //binding.etNumber.addTextChangedListener(loginViewModel.phonePasswordTextWatcher)
        //binding.etPassword.addTextChangedListener(loginViewModel.phonePasswordTextWatcher)
    }



    private fun handleItem(item: SignInResponseDataModel) {
        runBlocking {
            loginViewModel.saveToken(item.refresh, item.access)
        }
    }




}