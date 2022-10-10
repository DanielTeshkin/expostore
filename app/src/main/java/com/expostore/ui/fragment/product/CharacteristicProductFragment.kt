package com.expostore.ui.fragment.product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.CharacteristicProductFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.fragment.product.utils.CharacteristicsAdapter
import com.expostore.ui.fragment.profile.profile_edit.click

class CharacteristicProductFragment : BaseFragment<CharacteristicProductFragmentBinding>(CharacteristicProductFragmentBinding::inflate) {
    private val viewModel:CharacteristicProductViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        viewModel.apply {
            start(findNavController())
            subscribe(navigation) { navigateSafety(it) }
        }
        val result=CharacteristicProductFragmentArgs.fromBundle(requireArguments()).data.characteristics
        binding.rvCharacteristics.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=CharacteristicsAdapter(result)
        }
        binding.btnBack.click { viewModel.navigateBack() }
    }

}