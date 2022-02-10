package com.expostore.ui.authorization.newpassword

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
import com.expostore.databinding.NewPasswordFragmentBinding

class NewPasswordFragment : Fragment() {

    private lateinit var binding: NewPasswordFragmentBinding
    private lateinit var newPasswordViewModel: NewPasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_password_fragment, container, false)
        newPasswordViewModel = ViewModelProvider(this).get(NewPasswordViewModel::class.java)
        binding.newPasswordVM = newPasswordViewModel
        newPasswordViewModel.context = requireContext()

        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        newPasswordViewModel.phone = arguments?.getString("phoneInput")

        binding.btnNext.isEnabled = false

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
            binding.btnNext.isEnabled = firstPassword.isNotEmpty() && secondPassword.isNotEmpty() && firstPassword == secondPassword
        }
    }
}