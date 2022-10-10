package com.expostore.ui.fragment.authorization.confirmnumberpass


import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.expostore.databinding.ConfirmNumberResetFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.selects.selectUnbiased

@AndroidEntryPoint
class ConfirmNumberReset :
    BaseFragment<ConfirmNumberResetFragmentBinding>(ConfirmNumberResetFragmentBinding::inflate) {
    private val confirmNumberViewModel:ConfirmNumberResetViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        binding.etNumber.addTextChangedListener { confirmNumberViewModel.checkLengthNumber(it.toString()) }
        binding.btnBack.click { confirmNumberViewModel.toBack() }
        binding.tvNumberRules.click { confirmNumberViewModel.navigateToWeb() }
        confirmNumberViewModel.apply {
            start(findNavController())
            subscribe(navigation) { navigateSafety(it) }
            subscribe(loading){binding.loadBar2.isVisible=it}
        }
        binding.btnSignInNext.apply{
            state { confirmNumberViewModel.enabledState.collect { isEnabled=it }}
            click {  confirmNumberViewModel.confirmNumber() }
        }
    }


}