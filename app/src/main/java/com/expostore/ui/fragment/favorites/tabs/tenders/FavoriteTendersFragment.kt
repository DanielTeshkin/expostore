package com.expostore.ui.fragment.favorites.tabs.tenders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FavoriteTendersFragmentBinding
import com.expostore.model.favorite.FavoriteTender
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.favorites.FavoritesClickListener
import com.expostore.utils.OnClickFavoriteProductListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTendersFragment( private val installClickListener: FavoritesClickListener) : BaseFragment<FavoriteTendersFragmentBinding>(FavoriteTendersFragmentBinding::inflate) {

    private lateinit var onClickFavoriteProductListener: OnClickFavoriteProductListener
    private val tabFavoritesViewModel: FavoriteTendersViewModel by viewModels()
    private lateinit var mAdapter: FavoritesTenderRecyclerViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tabFavoritesViewModel.apply {
              getTenderFavoriteList()
                val show:Show<List<FavoriteTender>> = {showFavorites(it)}
                 subscribe(tenders){handleState(it,show)}
              subscribe(navigation){navigateSafety(it)} }

        }




        private fun showFavorites(item: List<FavoriteTender>) =
            binding.apply {
                rvFavorites.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                   mAdapter= FavoritesTenderRecyclerViewAdapter(item as MutableList<FavoriteTender>, context = requireContext(),installClickListener)
                    adapter = mAdapter
                    progressBar10.visibility = View.GONE
                }
            }

    }
