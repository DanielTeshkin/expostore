package com.expostore.ui.authorization.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginVM = loginViewModel
        loginViewModel.context = requireContext()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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