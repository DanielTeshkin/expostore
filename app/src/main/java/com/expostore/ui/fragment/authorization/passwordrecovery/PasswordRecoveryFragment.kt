package com.expostore.ui.fragment.authorization.passwordrecovery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.MainActivity
import com.expostore.databinding.PasswordRecoveryFragmentBinding
import com.expostore.ui.base.BaseFragment

class PasswordRecoveryFragment :
    BaseFragment<PasswordRecoveryFragmentBinding>(PasswordRecoveryFragmentBinding::inflate) {

    private lateinit var passwordRecoveryViewModel: PasswordRecoveryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        passwordRecoveryViewModel =
            ViewModelProvider(this).get(PasswordRecoveryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignInNext.setOnClickListener {
            passwordRecoveryViewModel.confirmCode(it, binding.etCode.text.toString())
        }

        binding.btnResendCode.setOnClickListener {
            passwordRecoveryViewModel.confirmNumber(it)
        }

        passwordRecoveryViewModel.context = requireContext()
        passwordRecoveryViewModel.btnResendCode = binding.btnResendCode
        passwordRecoveryViewModel.phoneInput = requireArguments().getString("phone")
        passwordRecoveryViewModel.timer.start()

        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    // Наблюдатель текста для кода
    private val codeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Если длина кода равна 6 символам, то кнопка "Продолжить" включена
            binding.btnSignInNext.isEnabled = binding.etCode.length() == 6
        }
    }
}