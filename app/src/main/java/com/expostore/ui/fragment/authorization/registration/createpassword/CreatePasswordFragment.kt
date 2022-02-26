package com.expostore.ui.fragment.authorization.registration.createpassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.CreatePasswordFragmentBinding
import com.expostore.ui.base.BaseFragment

class CreatePasswordFragment : BaseFragment<CreatePasswordFragmentBinding>(CreatePasswordFragmentBinding::inflate) {

    private lateinit var createPasswordViewModel: CreatePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPasswordViewModel = ViewModelProvider(this).get(CreatePasswordViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignInNext.setOnClickListener {
            createPasswordViewModel.signUp(it, binding.etSecondPassword.text.toString())
        }

        createPasswordViewModel.context = requireContext()

        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        createPasswordViewModel.phone = arguments?.getString("phoneInput")

        binding.btnSignInNext.isEnabled = false

        binding.etPassword.addTextChangedListener(passwordTextWatcher)
        binding.etSecondPassword.addTextChangedListener(passwordTextWatcher)
    }

    private val passwordTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val firstPassword: String = binding.etPassword.text.toString().trim()
            val secondPassword: String = binding.etSecondPassword.text.toString().trim()
            binding.btnSignInNext.isEnabled = firstPassword.isNotEmpty() && secondPassword.isNotEmpty() && firstPassword == secondPassword
        }
    }
}