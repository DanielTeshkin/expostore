package com.expostore.ui.fragment.favorites

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.expostore.databinding.FavoritesFragmentBinding
import com.expostore.model.SaveSearchModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFragment : BaseFragment<FavoritesFragmentBinding>(FavoritesFragmentBinding::inflate) {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesTabsViewPagerAdapter: FavoritesTabsViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }
        favoritesTabsViewPagerAdapter = FavoritesTabsViewPagerAdapter(this,requireContext(),installClickListener())
        binding.favoritesVp.adapter = favoritesTabsViewPagerAdapter
        binding.favoritesVp.offscreenPageLimit = favoritesTabsViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.favoritesTl,binding.favoritesVp) {
                tab, position -> tab.customView = favoritesTabsViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }

    fun installClickListener(): FavoritesClickListener {
        return object : FavoritesClickListener {
            override fun onClickSelection(selectionModel: SelectionModel, flag: String) {
                setFragmentResult("requestKey", bundleOf("selection" to selectionModel))
            //    setFragmentResult("requestKey", bundleOf("flag" to flag))
                favoritesViewModel.navigateToSelection()
            }

            override fun onClickSaveSearch(model: SaveSearchModel) {
                val result = FilterModel(
                    name = model.params?.q,
                    city = model.params?.city,
                    price_min = model.body_params.price_min,
                    price_max = model.body_params.price_max,
                    lat = model.body_params.lat,
                    long = model.body_params.long,
                    characteristics = model.body_params.characteristics,
                    sort = model.params?.sort,
                    category = model.body_params.category
                )
                setFragmentResult("requestKey", bundleOf("filters" to result))
                favoritesViewModel.navigateToSearch(model.type_search)
            }

            override fun onClickProduct(favoriteProduct: FavoriteProduct) {

                val result=favoriteProduct.product
                setFragmentResult("requestKey", bundleOf("product" to result))
                favoritesViewModel.navigateToProduct()
            }

            override fun onClickTender(favoriteProduct: FavoriteTender) {
                TODO("Not yet implemented")
            }


        }
    }
}