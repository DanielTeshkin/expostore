package com.expostore.ui.authorization.newpassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.NewPasswordFragmentBinding
import com.expostore.ui.base.BaseFragment

class NewPasswordFragment : BaseFragment<NewPasswordFragmentBinding>(NewPasswordFragmentBinding::inflate) {

    private lateinit var newPasswordViewModel: NewPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newPasswordViewModel = ViewModelProvider(this).get(NewPasswordViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            newPasswordViewModel.signUp(it, binding.etSecondPassword.text.toString())
        }

        newPasswordViewModel.context = requireContext()

        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        newPasswordViewModel.phone = arguments?.getString("phoneInput")

        binding.btnNext.isEnabled = false

        binding.etPassword.addTextChangedListener(passwordTextWatcher)
        binding.etSecondPassword.addTextChangedListener(passwordTextWatcher)
    }

    private val passwordTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val firstPassword: String = binding.etPassword.text.toString().trim()
            val secondPassword: String = binding.etSecondPassword.text.toString().trim()
            binding.btnNext.isEnabled = firstPassword.isNotEmpty() && secondPassword.isNotEmpty() && firstPassword == secondPassword
        }
    }
}