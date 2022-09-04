package com.expostore.ui.fragment.favorites.tabs.tenders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FavoriteTendersFragmentBinding
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.general.other.OnClickBottomSheetTenderFragment
import com.expostore.ui.general.other.showBottomSheetTender
import com.expostore.utils.OnClickFavoriteProductListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTendersFragment( ) : BaseFragment<FavoriteTendersFragmentBinding>(FavoriteTendersFragmentBinding::inflate),
    OnClickBottomSheetTenderFragment {

    private lateinit var onClickFavoriteProductListener: OnClickFavoriteProductListener
    private val tabFavoritesViewModel: FavoriteTendersViewModel by viewModels()
    private val tenders = mutableListOf<FavoriteTender>()
    private val mAdapter: FavoritesTenderRecyclerViewAdapter by lazy { FavoritesTenderRecyclerViewAdapter(tenders) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabFavoritesViewModel.apply {
            val show: Show<List<FavoriteTender>> = { showFavorites(it) }
            subscribe(tenders) { handleState(it, show) }
            subscribe(navigation) { navigateSafety(it) }
        }
    }



        private fun showFavorites(item: List<FavoriteTender>) =
            binding.apply {
                tenders.addAll(item)
                mAdapter.onCallItemClickListener={navigateToCall(it)}
                mAdapter.onClickLike={tabFavoritesViewModel.updateSelectedTender(it)}
                mAdapter.onItemClickListener={tabFavoritesViewModel.navigateToTenderItem(it)}
                mAdapter.onMessageItemClickListener={tabFavoritesViewModel.createChat(it)}
                mAdapter.onAnotherClickListener={ showBottomSheetTender(requireContext(),it,this@FavoriteTendersFragment)}
                rvFavorites.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = mAdapter
                }
                progressBar10.visibility = View.GONE
            }

    override fun call(username: String) =navigateToCall(username)

    override fun createNote(model: TenderModel)=tabFavoritesViewModel.navigateToNote(model)

    override fun chatCreate(id: String) =tabFavoritesViewModel.createChat(id)


    override fun block() {

    }

}
