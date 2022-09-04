package com.expostore.ui.fragment.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.databinding.SplashFragmentBinding
import com.expostore.ui.base.BaseFragment


class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override var isBottomNavViewVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            start(findNavController())
        }
    }

}