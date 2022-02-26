package com.expostore.ui.fragment.favorites.tabs.savedsearches

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.TabSavedSearchesFragmentBinding
import com.expostore.ui.base.BaseFragment

class TabSavedSearchesFragment :
    BaseFragment<TabSavedSearchesFragmentBinding>(TabSavedSearchesFragmentBinding::inflate) {

    companion object {
        fun newInstance() = TabSavedSearchesFragment()
    }

    private lateinit var viewModel: TabSavedSearchesViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TabSavedSearchesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}