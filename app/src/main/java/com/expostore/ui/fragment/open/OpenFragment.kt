package com.expostore.ui.fragment.open

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.data.AppPreferences
import com.expostore.databinding.OpenFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.main.MainFragmentDirections

class OpenFragment : BaseFragment<OpenFragmentBinding>(OpenFragmentBinding::inflate) {

    private lateinit var openViewModel: OpenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openViewModel = ViewModelProvider(this).get(OpenViewModel::class.java)

        if(!AppPreferences.getSharedPreferences(requireContext()).getString("token", "").isNullOrEmpty()) {
            navigateSafety(OpenFragmentDirections.actionOpenFragmentToMainFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            openViewModel.navigateToSignIn(it)
        }
        binding.btnSignUp.setOnClickListener {
            openViewModel.navigateToSignUp(it)
        }

        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        //TODO Добавить в клик кнопки выхода
        //(context as MainActivity).sharedPreferences.edit().clear().apply()
    }
}