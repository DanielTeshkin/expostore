package com.expostore.ui.fragment.product.myproducts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.expostore.databinding.MyProductsFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.product.myproducts.adapter.MyProductClickRepository
import com.expostore.ui.fragment.product.myproducts.adapter.MyProductsTabsViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProductsFragment () : BaseFragment<MyProductsFragmentBinding>(MyProductsFragmentBinding::inflate) {

      private val viewModel:BaseMyProductViewModel by viewModels()
    private lateinit var myProductsTabsViewPagerAdapter: MyProductsTabsViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }

        myProductsTabsViewPagerAdapter = MyProductsTabsViewPagerAdapter(this,requireContext())
        binding.myProductsViewPager.adapter = myProductsTabsViewPagerAdapter
        binding.myProductsViewPager.offscreenPageLimit = myProductsTabsViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.myProductsTabLayout,binding.myProductsViewPager) {
                tab, position -> tab.customView = myProductsTabsViewPagerAdapter.getTabView(position)
        }
       tabLayoutMediator.attach()
    }
}