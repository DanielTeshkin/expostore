package com.expostore.ui.fragment.category.personal.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.expostore.R
import com.expostore.databinding.CreatePersonalSelectionFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePersonalSelectionFragment : BaseFragment<CreatePersonalSelectionFragmentBinding>(CreatePersonalSelectionFragmentBinding::inflate) {
    private val viewModel:CreatePersonalSelectionViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("name"){_,bundle->
            val result=bundle.getString("product")
            if (result != null) {
                viewModel.saveProduct(result)
            }
        }
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }
        binding.saving.click {
            val text=binding.searchEt.text.toString()
            viewModel.createSelection(text)
        }
    }
}