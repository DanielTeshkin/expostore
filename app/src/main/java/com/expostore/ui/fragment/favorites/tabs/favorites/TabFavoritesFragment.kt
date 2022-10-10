package com.expostore.ui.fragment.favorites.tabs.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.ui.base.fragments.BaseProductFragment
import com.expostore.ui.fragment.favorites.FavoriteSharedRepository
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
        viewModel.apply {
            getFavorites()
            getSelections()
            subscribe(favorites) { state ->
                handleState(state) {
                    binding.apply {
                        rvFavorites.apply {
                            if(it.isNotEmpty()) {
                                favoritesList.addAll(it)
                                layoutManager = LinearLayoutManager(requireContext())
                                adapter = myAdapter
                                progressBar4.visibility = View.GONE
                            }
                        }
                    }
                }
            } }
    }

    override fun loadSelections(list: List<SelectionModel>) {
        FavoriteSharedRepository.getInstance().getSelections().value=list
        myAdapter.onClickListener=getClickListener(list)
    }


}