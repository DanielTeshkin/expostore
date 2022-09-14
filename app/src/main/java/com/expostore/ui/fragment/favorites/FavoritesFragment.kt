package com.expostore.ui.fragment.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FavoritesFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FavoritesFragmentBinding>(FavoritesFragmentBinding::inflate) {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesTabsViewPagerAdapter: FavoritesTabsViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val selections:MutableList<SelectionModel> by lazy { mutableListOf() }
    private val selectionsAdapter by lazy { SelectionsAdapter(selections) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(FavoriteSharedRepository.getInstance().getSelections()){loadSelections(it)}
        }
        favoritesTabsViewPagerAdapter = FavoritesTabsViewPagerAdapter(this,requireContext())
        binding.favoritesVp.adapter = favoritesTabsViewPagerAdapter
        binding.favoritesVp.offscreenPageLimit = favoritesTabsViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.favoritesTl,binding.favoritesVp) {
                tab, position -> tab.customView = favoritesTabsViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }

    override fun onStart() {
        super.onStart()
        binding.newSelection.click{favoritesViewModel.navigateToCreateSelection()}
        binding.comparisonBtn.click { favoritesViewModel.navigateToComparison() }
    }

    private fun loadSelections(list: List<SelectionModel>){
        selections.addAll(list)
        selectionsAdapter.onClick={ favoritesViewModel.navigateToSelection(it)}
        binding.rvSelections.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=selectionsAdapter

        }
    }


}