package com.expostore.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.R
import com.expostore.databinding.LoginFragmentBinding
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.ui.authorization.login.LoginViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.searchVM = searchViewModel

        return binding.root
    }

}