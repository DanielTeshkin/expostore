package com.expostore.ui.fragment.favorites.tabs.selections

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.TabSelectionsFragmentBinding
import com.expostore.ui.base.BaseFragment

class TabSelectionsFragment :
    BaseFragment<TabSelectionsFragmentBinding>(TabSelectionsFragmentBinding::inflate) {

    companion object {
        fun newInstance() = TabSelectionsFragment()
    }

    private lateinit var viewModel: TabSelectionsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TabSelectionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}