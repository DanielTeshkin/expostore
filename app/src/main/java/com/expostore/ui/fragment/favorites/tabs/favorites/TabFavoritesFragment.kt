package com.expostore.ui.fragment.favorites.tabs.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState

import com.expostore.utils.FavoritesProductRecyclerViewAdapter
import com.expostore.utils.OnClickFavoriteProductListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TabFavoritesFragment :
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
         }
          tabFavoritesViewModel.apply {
              loadFavoriteList()
              subscribe(favoriteList){handleState(it)}
              subscribe(navigation){navigateSafety(it)}
          }
    }

    private fun handleState(state: ResponseState<List<FavoriteProduct>>) {
       when(state){
           is ResponseState.Success ->showFavorites(state.item)
           is ResponseState.Error->Toast.makeText(requireContext(),state.throwable.message,Toast.LENGTH_LONG).show()
       }
    }

    private fun showFavorites(item: List<FavoriteProduct>) =
        binding.apply {
            rvFavorites.apply {
            layoutManager=LinearLayoutManager(requireContext())
            mAdapter= FavoritesProductRecyclerViewAdapter(item,
                onClickFavoriteProductListener,
                requireContext())
            adapter = mAdapter
            progressBar4.visibility=View.GONE
        }}
    }