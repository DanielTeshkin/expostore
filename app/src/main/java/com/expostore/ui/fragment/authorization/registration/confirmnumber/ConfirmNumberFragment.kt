package com.expostore.ui.fragment.authorization.registration.confirmnumber

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.databinding.ConfirmNumberFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.state.ResponseState
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class ConfirmNumberFragment :
    BaseFragment<ConfirmNumberFragmentBinding>(ConfirmNumberFragmentBinding::inflate) {

    private  val confirmNumberViewModel:ConfirmNumberViewModel by viewModels()

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
