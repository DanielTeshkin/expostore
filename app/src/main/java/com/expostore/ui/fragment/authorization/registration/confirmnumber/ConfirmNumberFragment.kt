package com.expostore.ui.fragment.authorization.registration.confirmnumber

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.databinding.ConfirmNumberFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.ui.fragment.profile.profile_edit.click
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
        binding.tvNumberRules.click { confirmNumberViewModel.navigateToWeb() }
        confirmNumberViewModel.apply {
            start(findNavController())
            subscribe(navigation) { navigateSafety(it) }
            subscribe(loading){binding.loadBar3.isVisible=it}
            binding.btnSignInNext.apply {
                subscribe(enabledState) { isEnabled = it }
                click { confirmNumber() }
            }
        }
    }

    }
