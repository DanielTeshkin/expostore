package com.expostore.ui.fragment.authorization.newpassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.NewPasswordFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewPasswordFragment : BaseFragment<NewPasswordFragmentBinding>(NewPasswordFragmentBinding::inflate) {
   private val viewModel: NewPasswordViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        val phone=NewPasswordFragmentArgs.fromBundle(requireArguments()).phone
        subscribe(viewModel.navigation){ navigateSafety(it) }
        binding.btnNext.click {
            viewModel.reset(phone,
            binding.etPassword.text.toString(),
                binding.etSecondPassword.text.toString()
                )
        }
    }



}