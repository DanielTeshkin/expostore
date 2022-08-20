package com.expostore.ui.fragment.tender.my

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.expostore.databinding.MyTenderListFragmentBinding
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTenderListFragment : BaseFragment<MyTenderListFragmentBinding>(MyTenderListFragmentBinding::inflate) {
    private val viewModel: MyTenderListViewModel by viewModels()
    private lateinit var myProductsTabsViewPagerAdapter: TabTenderAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }


        myProductsTabsViewPagerAdapter = TabTenderAdapter(this,requireContext())
        binding.myProductsViewPager.adapter = myProductsTabsViewPagerAdapter
        binding.myProductsViewPager.offscreenPageLimit = myProductsTabsViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.myProductsTabLayout,binding.myProductsViewPager) {
                tab, position -> tab.customView = myProductsTabsViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }


}