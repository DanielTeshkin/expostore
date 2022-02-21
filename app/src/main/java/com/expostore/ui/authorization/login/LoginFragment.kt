package com.expostore.ui.authorization.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.R
import com.expostore.databinding.LoginFragmentBinding
import com.expostore.ui.base.BaseFragment

class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignInNext.setOnClickListener {
            loginViewModel.signIn(
                it,
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
        binding.etNumber.addTextChangedListener(loginViewModel.phonePasswordTextWatcher)
        binding.etPassword.addTextChangedListener(loginViewModel.phonePasswordTextWatcher)
    }
}