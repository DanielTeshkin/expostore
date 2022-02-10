package com.expostore.ui.authorization.registration.createpassword

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
import com.expostore.databinding.CreatePasswordFragmentBinding

class CreatePasswordFragment : Fragment() {

    private lateinit var binding: CreatePasswordFragmentBinding
    private lateinit var createPasswordViewModel: CreatePasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_password_fragment, container, false)
        createPasswordViewModel = ViewModelProvider(this).get(CreatePasswordViewModel::class.java)
        binding.createPasswordVM = createPasswordViewModel
        createPasswordViewModel.context = requireContext()

        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        createPasswordViewModel.phone = arguments?.getString("phoneInput")

        binding.btnSignInNext.isEnabled = false

        binding.etPassword.addTextChangedListener(passwordTextWatcher)
        binding.etSecondPassword.addTextChangedListener(passwordTextWatcher)

        return binding.root
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