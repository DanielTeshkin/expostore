package com.expostore.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.R
import com.expostore.databinding.MyProductsFragmentBinding
import com.expostore.databinding.ProfileFragmentBinding
import com.expostore.ui.product.myproducts.MyProductsTabsViewPagerAdapter
import com.expostore.ui.product.myproducts.MyProductsViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.profileVM = profileViewModel

        return binding.root
    }

}