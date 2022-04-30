package com.expostore.ui.fragment.authorization.registration.confirmnumber

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.databinding.ConfirmNumberFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class ConfirmNumberFragment :
    BaseFragment<ConfirmNumberFragmentBinding>(ConfirmNumberFragmentBinding::inflate) {

    private  val confirmNumberViewModel:ConfirmNumberViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
               confirmNumberViewModel.toBack()
           }
        binding.btnSignInNext.setOnClickListener{
            confirmNumberViewModel.confirmNumber("7"+binding.etNumber.text.toString())
            observe()
        }
        }

    fun observe(){
        confirmNumberViewModel.apply {
            singleSubscribe(navigation) { navigateSafety(it) }
        }
    } }
