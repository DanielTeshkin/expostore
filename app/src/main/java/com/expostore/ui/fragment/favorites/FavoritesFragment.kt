package com.expostore.ui.fragment.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.FavoritesFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFragment : BaseFragment<FavoritesFragmentBinding>(FavoritesFragmentBinding::inflate) {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var favoritesTabsViewPagerAdapter: FavoritesTabsViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesTabsViewPagerAdapter = FavoritesTabsViewPagerAdapter(this,requireContext())
        binding.favoritesVp.adapter = favoritesTabsViewPagerAdapter
        binding.favoritesVp.offscreenPageLimit = favoritesTabsViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.favoritesTl,binding.favoritesVp) {
                tab, position -> tab.customView = favoritesTabsViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }
}