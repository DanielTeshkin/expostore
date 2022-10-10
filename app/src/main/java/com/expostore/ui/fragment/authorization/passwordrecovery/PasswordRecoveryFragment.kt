package com.expostore.ui.fragment.authorization.passwordrecovery

import android.os.Bundle
import com.expostore.ui.base.fragments.BaseFragment

import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels

import com.expostore.R
import com.expostore.databinding.PasswordRecoveryFragmentBinding
import com.expostore.ui.fragment.authorization.registration.confirmcode.ConfirmCodeFragmentArgs
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint
class PasswordRecoveryFragment :
    BaseFragment<PasswordRecoveryFragmentBinding>(PasswordRecoveryFragmentBinding::inflate) {

    private  val passwordRecoveryViewModel: PasswordRecoveryViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val phone= ConfirmCodeFragmentArgs.fromBundle(requireArguments()).phone
        passwordRecoveryViewModel.saveNumber(phone)
        binding.subtitle.text = getString(R.string.confirm_number_subtitle_text, phone)
        binding.btnBack.setOnClickListener {
            passwordRecoveryViewModel.back()
        }
        binding.btnSignInNext.setOnClickListener {
            passwordRecoveryViewModel.confirmCode()
            passwordRecoveryViewModel.apply {
                subscribe(navigation) { navigateSafety(it) }
               subscribe(enabled){binding.btnSignInNext.isEnabled=it}
                subscribe(loading){binding.progressBar15.isVisible=it}
            }
        }
    }

    override fun onStart() {
        super.onStart()
        passwordRecoveryViewModel.timerStart()
        binding.etCode.addTextChangedListener { passwordRecoveryViewModel.saveInput(it.toString()) }
        state { passwordRecoveryViewModel.resetText.collect {  binding.btnResendCode.text=it} }
        state { passwordRecoveryViewModel.clickable.collect { binding.btnResendCode.isClickable=it } }
        binding.btnResendCode.click {
            passwordRecoveryViewModel.confirmNumber()
            passwordRecoveryViewModel.timerStart()
        }


    }
}