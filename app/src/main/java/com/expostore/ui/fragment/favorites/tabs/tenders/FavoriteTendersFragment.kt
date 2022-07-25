package com.expostore.ui.fragment.favorites.tabs.tenders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.api.request.NoteRequest
import com.expostore.databinding.FavoriteTendersFragmentBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.favorites.FavoritesClickListener
import com.expostore.ui.fragment.favorites.tabs.favorites.TabFavoritesViewModel
import com.expostore.utils.FavoritesProductRecyclerViewAdapter
import com.expostore.utils.OnClickFavoriteProductListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTendersFragment(installClickListener: FavoritesClickListener) : BaseFragment<FavoriteTendersFragmentBinding>(FavoriteTendersFragmentBinding::inflate) {

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
                   mAdapter= FavoritesTenderRecyclerViewAdapter(item as MutableList<FavoriteTender>, context = requireContext())
                    adapter = mAdapter
                    progressBar10.visibility = View.GONE
                }
            }

    }
