package com.expostore.ui.favorites.tabs.savedsearches

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.R

class TabSavedSearchesFragment : Fragment() {

    companion object {
        fun newInstance() = TabSavedSearchesFragment()
    }

    private lateinit var viewModel: TabSavedSearchesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_saved_searches_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TabSavedSearchesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}