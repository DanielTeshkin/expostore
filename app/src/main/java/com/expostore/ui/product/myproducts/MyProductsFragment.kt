package com.expostore.ui.product.myproducts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.R
import com.expostore.databinding.MyProductsFragmentBinding
import com.expostore.ui.favorites.FavoritesTabsViewPagerAdapter
import com.expostore.ui.favorites.FavoritesViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MyProductsFragment : Fragment() {

    private lateinit var binding: MyProductsFragmentBinding
    private lateinit var myProductsViewModel: MyProductsViewModel
    private lateinit var myProductsTabsViewPagerAdapter: MyProductsTabsViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_products_fragment, container, false)
        myProductsViewModel = ViewModelProvider(this).get(MyProductsViewModel::class.java)
        binding.myProductsVM = myProductsViewModel

        return binding.root
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