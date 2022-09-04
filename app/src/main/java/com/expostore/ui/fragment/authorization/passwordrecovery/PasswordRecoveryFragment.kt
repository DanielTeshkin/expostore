package com.expostore.ui.fragment.authorization.passwordrecovery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.PasswordRecoveryFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.authorization.registration.confirmcode.ConfirmCodeFragmentArgs
import com.expostore.ui.fragment.authorization.registration.confirmcode.ConfirmCodeViewModel
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
                subscribe(state){handleState(it)}
            }
        }
    }

    override fun onStart() {
        super.onStart()
        passwordRecoveryViewModel.timerStart()
        binding.etCode.addTextChangedListener {
            passwordRecoveryViewModel.saveInput(it.toString())
            binding.btnSignInNext.isEnabled=it?.length==6
        }
        state { passwordRecoveryViewModel.resetText.collect {  binding.btnResendCode.text=it} }
        state { passwordRecoveryViewModel.clickable.collect { binding.btnResendCode.isClickable=it } }
        binding.btnResendCode.click {
            passwordRecoveryViewModel.confirmNumber()
            passwordRecoveryViewModel.timerStart()
        }


    }
}