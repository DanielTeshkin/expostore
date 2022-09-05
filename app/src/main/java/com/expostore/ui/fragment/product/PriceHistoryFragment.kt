package com.expostore.ui.fragment.product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.PriceHistoryFragmentBinding
import com.expostore.model.product.PriceHistoryModel
import com.expostore.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


class PriceHistoryFragment : BaseFragment<PriceHistoryFragmentBinding>(PriceHistoryFragmentBinding::inflate) {
   private val viewModel :PriceHistoryViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        val result=PriceHistoryFragmentArgs.fromBundle(requireArguments()).priceHistory
        binding.rvPrice.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=PriceHistoryRecyclerView(result.list)
        }
        subscribe(viewModel.navigation){navigateSafety(it)}
    }

}