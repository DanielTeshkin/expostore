package com.expostore.ui.fragment.favorites.tabs.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.BaseProductFragment
import com.expostore.ui.base.BaseProductViewModel
import com.expostore.ui.base.Show
import com.expostore.ui.controllers.TabFavoritesController
import com.expostore.ui.fragment.favorites.FavoriteSharedRepository
import com.expostore.ui.general.other.OnClickBottomSheetFragment

import com.expostore.utils.FavoritesProductRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFavoritesFragment() :
    BaseProductFragment<TabFavoritesFragmentBinding>(TabFavoritesFragmentBinding::inflate){
    override val viewModel: TabFavoritesViewModel by viewModels()
    private val controller : TabFavoritesController by lazy { TabFavoritesController(binding,requireContext()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.apply {
            val show:Show<List<FavoriteProduct>> = {controller.showFavorites(it)}
              subscribe(favoriteList){handleState(it,show)}
              subscribe(navigation){navigateSafety(it)}
               subscribe(selections){ it ->
                   handleState(it) {
                       FavoriteSharedRepository.getInstance().getSelections().value=it
                       controller.setEvent(getClickListener(it))
                   }
               }
        }
    }

    override fun onStart() {
        super.onStart()
       // state {
          //  tabFavoritesViewModel.selections.collect { selections->
             //   mAdapter.onAnotherClickListener = {
                  //  showBottomSheet(
                      //  requireContext(),
                      //  onClickBottomFragment = this@TabFavoritesFragment,
                    //    list = selections,
                      //  model = it
                            //  )
                        // }
           // }
       // }
    }







}