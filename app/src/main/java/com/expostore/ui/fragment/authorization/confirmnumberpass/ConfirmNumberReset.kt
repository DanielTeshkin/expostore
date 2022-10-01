package com.expostore.ui.fragment.authorization.confirmnumberpass


import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels

import com.expostore.databinding.ConfirmNumberResetFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ConfirmNumberReset :
    BaseFragment<ConfirmNumberResetFragmentBinding>(ConfirmNumberResetFragmentBinding::inflate) {
    private val confirmNumberViewModel:ConfirmNumberResetViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        binding.etNumber.addTextChangedListener { confirmNumberViewModel.checkLengthNumber(it.toString()) }
        binding.btnBack.click { confirmNumberViewModel.toBack() }
        confirmNumberViewModel.apply {
            subscribe(navigation) { navigateSafety(it) }
        }
        binding.btnSignInNext.apply{
            state { confirmNumberViewModel.enabledState.collect { isEnabled=it }}
            click {  confirmNumberViewModel.confirmNumber() }
        }
    }


}