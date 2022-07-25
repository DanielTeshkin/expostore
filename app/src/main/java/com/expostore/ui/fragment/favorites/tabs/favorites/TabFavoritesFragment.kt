package com.expostore.ui.fragment.favorites.tabs.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.request.NoteRequest
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.favorites.FavoritesClickListener

import com.expostore.utils.FavoritesProductRecyclerViewAdapter
import com.expostore.utils.OnClickFavoriteProductListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFavoritesFragment( private val installClickListener: FavoritesClickListener) :
    BaseFragment<TabFavoritesFragmentBinding>(TabFavoritesFragmentBinding::inflate){
 private  lateinit var onClickFavoriteProductListener:OnClickFavoriteProductListener
    private val tabFavoritesViewModel: TabFavoritesViewModel by viewModels()
    private lateinit var mAdapter: FavoritesProductRecyclerViewAdapter
    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         onClickFavoriteProductListener=object : OnClickFavoriteProductListener{
            override fun onClickProduct(model: ProductModel) {
                       tabFavoritesViewModel.navigation()
            }

            override fun onClickLike(id: String) {
                tabFavoritesViewModel.apply {
                    update(id)
                }

            }

             override fun createNote(id: String, text: String) {
                tabFavoritesViewModel.updateOrCreateNote(id, NoteRequest(text = text))
             }
         }
          tabFavoritesViewModel.apply {
              loadFavoriteList()
              val show:Show<List<FavoriteProduct>> = {showFavorites(it)}
              subscribe(favoriteList){handleState(it,show)}
              subscribe(navigation){navigateSafety(it)} }
    }



    private fun showFavorites(item: List<FavoriteProduct>) =
        binding.apply {
            rvFavorites.apply {
            layoutManager=LinearLayoutManager(requireContext())
            mAdapter= FavoritesProductRecyclerViewAdapter(item as MutableList<FavoriteProduct>,
                onClickFavoriteProductListener,
                installClickListener,
                requireContext())
            adapter = mAdapter
            progressBar4.visibility=View.GONE
        }}
    }