package com.expostore.ui.fragment.product.myproducts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.MyProductsFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class MyProductsFragment : BaseFragment<MyProductsFragmentBinding>(MyProductsFragmentBinding::inflate) {

    private lateinit var myProductsViewModel: MyProductsViewModel
    private lateinit var myProductsTabsViewPagerAdapter: MyProductsTabsViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myProductsViewModel = ViewModelProvider(this).get(MyProductsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myProductsTabsViewPagerAdapter = MyProductsTabsViewPagerAdapter(this,requireContext())
        binding.myProductsViewPager.adapter = myProductsTabsViewPagerAdapter
        binding.myProductsViewPager.offscreenPageLimit = myProductsTabsViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.myProductsTabLayout,binding.myProductsViewPager) {
                tab, position -> tab.customView = myProductsTabsViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }
}