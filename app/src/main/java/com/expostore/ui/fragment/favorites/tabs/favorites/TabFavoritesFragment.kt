package com.expostore.ui.fragment.favorites.tabs.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
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
import com.expostore.ui.fragment.favorites.tabs.selections.TabSelectionsViewModel
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet

import com.expostore.utils.FavoritesProductRecyclerViewAdapter
import com.expostore.utils.OnClickFavoriteProductListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TabFavoritesFragment( private val installClickListener: FavoritesClickListener) :
    BaseFragment<TabFavoritesFragmentBinding>(TabFavoritesFragmentBinding::inflate),OnClickBottomSheetFragment{
 private  lateinit var onClickFavoriteProductListener:OnClickFavoriteProductListener
    private val tabFavoritesViewModel: TabFavoritesViewModel by viewModels()
    private lateinit var mAdapter: FavoritesProductRecyclerViewAdapter
    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabFavoritesViewModel.apply {
              loadFavoriteList()
              val show:Show<List<FavoriteProduct>> = {showFavorites(it)}
              subscribe(favoriteList){handleState(it,show)}
              subscribe(navigation){navigateSafety(it)} }
    }

    override fun onStart() {
        super.onStart()
        state {
            tabFavoritesViewModel.selections.collect { selections->
                mAdapter.onAnotherClickListener = {
                    showBottomSheet(
                        requireContext(),
                        onClickBottomFragment = this@TabFavoritesFragment,
                        list = selections,
                        personalOrNot = false,
                        model = it
                    )
                }
            }
        }
    }


    private fun showFavorites(item: List<FavoriteProduct>) {

        binding.apply {
            rvFavorites.apply {
                layoutManager = LinearLayoutManager(requireContext())
                mAdapter = FavoritesProductRecyclerViewAdapter(
                    item as MutableList<FavoriteProduct>,
                    installClickListener,
                    requireContext()
                )
                mAdapter.onCallItemClickListener={navigateToCall(it)}
                mAdapter.onMessageItemClickListener= {tabFavoritesViewModel.createChat(it.id)}
                mAdapter.onClickLike={ tabFavoritesViewModel.update(it)}
                adapter = mAdapter
                progressBar4.visibility = View.GONE
            }
        }
    }

    override fun createSelection(product: String) {

    }

    override fun addToSelection(id: String, product: String) {
        TODO("Not yet implemented")
    }

    override fun call(username: String) {
        navigateToCall(username)
    }

    override fun createNote(product: ProductModel) {
        TODO("Not yet implemented")
    }

    override fun chatCreate(id: String) {
        tabFavoritesViewModel.createChat(id)
    }

    override fun share(id: String) {
        TODO("Not yet implemented")
    }

    override fun block() {
        TODO("Not yet implemented")
    }


}