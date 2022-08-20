package com.expostore.ui.fragment.favorites

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FavoritesFragmentBinding
import com.expostore.model.SaveSearchModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FavoritesFragmentBinding>(FavoritesFragmentBinding::inflate) {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesTabsViewPagerAdapter: FavoritesTabsViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val selections:MutableList<SelectionModel> by lazy { mutableListOf() }
    private val selectionsAdapter by lazy { SelectionsAdapter(selections) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val show:Show<List<SelectionModel>> ={ loadSelections(it)}
        favoritesViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(state){handleState(it,show)}
        }
        favoritesTabsViewPagerAdapter = FavoritesTabsViewPagerAdapter(this,requireContext(),installClickListener())
        binding.favoritesVp.adapter = favoritesTabsViewPagerAdapter
        binding.favoritesVp.offscreenPageLimit = favoritesTabsViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.favoritesTl,binding.favoritesVp) {
                tab, position -> tab.customView = favoritesTabsViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }

    override fun onStart() {
        super.onStart()
        binding.newSelection.click{favoritesViewModel.navigateToCreateSelection()}
    }

    private fun loadSelections(list: List<SelectionModel>){
        selections.addAll(list)
        selectionsAdapter.onClick={
            setFragmentResult("requestKey", bundleOf("selection" to it))
            favoritesViewModel.navigateToSelection()
        }
        binding.rvSelections.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=selectionsAdapter

        }
    }

   private fun installClickListener(): FavoritesClickListener {
        return object : FavoritesClickListener {
            override fun onClickSelection(selectionModel: SelectionModel, flag: String) {
                setFragmentResult("requestKey", bundleOf("selection" to selectionModel))
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

                setFragmentResult("requestNote", bundleOf("text" to favoriteProduct.notes))
                favoritesViewModel.navigateToProduct(favoriteProduct.product)
            }

            override fun onClickTender(favoriteProduct: FavoriteTender) {
             favoritesViewModel.navigateToTender(favoriteProduct.tender)
            }


        }
    }
}