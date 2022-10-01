package com.expostore.ui.fragment.favorites.tabs.tenders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FavoriteTendersFragmentBinding
import com.expostore.model.favorite.FavoriteTender
import com.expostore.ui.base.fragments.BaseTenderFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.general.other.showBottomSheetTender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTendersFragment : BaseTenderFragment<FavoriteTendersFragmentBinding>(FavoriteTendersFragmentBinding::inflate){

    override  val viewModel: FavoriteTendersViewModel by viewModels()
    private val tenders = mutableListOf<FavoriteTender>()
    private val mAdapter: FavoritesTenderRecyclerViewAdapter by lazy { FavoritesTenderRecyclerViewAdapter(tenders) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.apply {
            val show: Show<List<FavoriteTender>> = { showFavorites(it) }
            subscribe(favorites) { handleState(it, show) }
            subscribe(navigation) { navigateSafety(it) }
        }
    }

    private fun showFavorites(item: List<FavoriteTender>) =
            binding.apply {
                tenders.addAll(item)
                mAdapter.onCallItemClickListener={navigateToCall(it)}
                mAdapter.onClickLike={updateFavorites(it)}
                mAdapter.onItemClickListener={viewModel.navigateToItem(it)}
                mAdapter.onMessageItemClickListener={viewModel.createChat(it)}
               mAdapter.onAnotherClickListener={ showBottomSheetTender(requireContext(),it,this@FavoriteTendersFragment)}
                rvFavorites.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = mAdapter
                }
                progressBar10.visibility = View.GONE
            }




}
