package com.expostore.ui.authorization.passwordrecovery

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
import com.expostore.databinding.PasswordRecoveryFragmentBinding
import com.expostore.ui.authorization.login.LoginViewModel

class PasswordRecoveryFragment : Fragment() {

    private lateinit var binding: PasswordRecoveryFragmentBinding
    private lateinit var passwordRecoveryViewModel: PasswordRecoveryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.password_recovery_fragment, container, false)
        passwordRecoveryViewModel = ViewModelProvider(this).get(PasswordRecoveryViewModel::class.java)
        binding.passwordRecoveryVM = passwordRecoveryViewModel

        passwordRecoveryViewModel.context = requireContext()
        passwordRecoveryViewModel.btnResendCode = binding.btnResendCode
        passwordRecoveryViewModel.phoneInput = requireArguments().getString("phone")
        passwordRecoveryViewModel.timer.start()

        (context as MainActivity).navView.visibility = View.GONE

        return binding.root
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