package com.expostore.ui.fragment.favorites.tabs.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.*
import com.expostore.ui.controllers.TabFavoritesController
import com.expostore.ui.fragment.favorites.FavoriteSharedRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFavoritesFragment() :
    BaseProductListFragment<TabFavoritesFragmentBinding>(TabFavoritesFragmentBinding::inflate){
    override val viewModel: TabFavoritesViewModel by viewModels()
    private val controller: TabFavoritesController by lazy { TabFavoritesController(binding,requireContext()) }

    override fun onStart() {
        super.onStart()
        viewModel.apply {
            subscribe(favoriteList) { state ->
                handleState(state) {
                    controller.showFavorites(it)
                }
            } }
    }

    override fun loadSelections(list: List<SelectionModel>) {
        FavoriteSharedRepository.getInstance().getSelections().value=list
        controller.setEvent(getClickListener(list))
    }


}