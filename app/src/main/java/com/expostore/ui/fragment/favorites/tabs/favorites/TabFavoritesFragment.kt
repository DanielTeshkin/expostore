package com.expostore.ui.fragment.favorites.tabs.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.ui.base.fragments.BaseProductFragment
import com.expostore.ui.fragment.favorites.SelectionsSharedRepository
import com.expostore.utils.FavoritesProductRecyclerViewAdapter

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TabFavoritesFragment :
    BaseProductFragment<TabFavoritesFragmentBinding>(TabFavoritesFragmentBinding::inflate){
    override val viewModel: TabFavoritesViewModel by viewModels()
    private val favoritesList = mutableListOf<FavoriteProduct>()
    private val myAdapter : FavoritesProductRecyclerViewAdapter by lazy{
        FavoritesProductRecyclerViewAdapter(products = favoritesList) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply { subscribe(favorites) { state -> handleState(state) {installAdapter(it)} }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            getFavorites()
            getSelections()
        }
    }


    private fun installAdapter(items:List<FavoriteProduct>){
        favoritesList.clear()
        if(items.isNotEmpty()) {
            favoritesList.addAll(items)
            binding.progressBar4.visibility = View.GONE
        }
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
    }

    override fun loadSelections(list: List<SelectionModel>) {
       SelectionsSharedRepository.getInstance().getSelections().value=list
        myAdapter.onClickListener=getClickListener(list)}



}