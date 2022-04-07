package com.expostore.ui.fragment.search.filter

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.databinding.SearchFilterFragmentBinding
import com.expostore.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Fedotov Yakov
 */
@AndroidEntryPoint
class SearchFilterFragment :
    BaseFragment<SearchFilterFragmentBinding>(SearchFilterFragmentBinding::inflate) {

    private val viewModel: SearchFilterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            back.setOnClickListener { findNavController().popBackStack() }

            production.setOnClickListener {
                it.isSelected = !it.isSelected
                productionGroup.isVisible = it.isSelected
            }
        }
    }

}