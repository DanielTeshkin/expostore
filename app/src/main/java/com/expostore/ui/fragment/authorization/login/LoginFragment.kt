package com.expostore.ui.fragment.authorization.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.expostore.R
import com.expostore.data.AppPreferences
import com.expostore.databinding.LoginFragmentBinding
import com.expostore.model.auth.SignInResponseDataModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    override var isBottomNavViewVisible = false

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.apply {
            subscribe(navigation) { navigateSafety(it) }
            subscribe(uiState) { handleState(it) }
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

    private fun handleState(state: ResponseState<SignInResponseDataModel>) {
        when (state) {
            is ResponseState.Loading -> handleLoading(state.isLoading)
            is ResponseState.Error -> handleError(state.throwable)
            is ResponseState.Success -> handleItem(state.item)
        }
    }

    private fun handleItem(item: SignInResponseDataModel) {
        AppPreferences.getSharedPreferences(requireContext()).edit().putString("token", item.access)
            .putString("refresh", item.refresh)
            .apply()
    }

    private fun handleError(throwable: Throwable) {
        // TODO: сделать отображение ошибки с сервера
        //временная реализация
        throwable.message?.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleLoading(loading: Boolean) {
        // TODO: сделать отображение загрузки
    }
}