package com.expostore.ui.fragment.authorization.registration.confirmcode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.expostore.R

import com.expostore.databinding.ConfirmCodeFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class ConfirmCodeFragment : BaseFragment<ConfirmCodeFragmentBinding>(ConfirmCodeFragmentBinding::inflate) {

    private val confirmCodeViewModel: ConfirmCodeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val phone = ConfirmCodeFragmentArgs.fromBundle(requireArguments()).phone
        confirmCodeViewModel.saveNumber(phone)
        binding.subtitle.text = getString(R.string.confirm_number_subtitle_text, phone)
        binding.imageButton.setOnClickListener {
            confirmCodeViewModel.back()
        }
        binding.btnSignInNext.setOnClickListener {
            confirmCodeViewModel.confirmCode()
            confirmCodeViewModel.apply {
                subscribe(navigation) { navigateSafety(it) }
                subscribe(state) { handleState(it) }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        confirmCodeViewModel.timerStart()
        binding.etNumber.addTextChangedListener {
            confirmCodeViewModel.saveInput(it.toString())
            binding.btnSignInNext.isEnabled = it?.length == 6
        }
        state { confirmCodeViewModel.resetText.collect { binding.btnResendCode.text = it } }
        state { confirmCodeViewModel.clickable.collect { binding.btnResendCode.isClickable = it } }
        binding.btnResendCode.click {
            confirmCodeViewModel.confirmNumber()
            confirmCodeViewModel.timerStart()
        }

    }
}



