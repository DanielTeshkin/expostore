package com.expostore.ui.fragment.search.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.search.fragments.BaseSearchFragment
import com.expostore.ui.base.search.fragments.Image
import com.expostore.ui.base.search.fragments.Location
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.main.adapter.ProductMarkerApi
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.expostore.ui.fragment.specifications.DataModel
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseSearchFragment<SearchFragmentBinding, ProductModel, SelectFavoriteResponseData, FavoriteProduct>(SearchFragmentBinding::inflate) {

    override val viewModel: SearchViewModel by viewModels()
    private val myAdapter: ProductsAdapter by lazy { ProductsAdapter(requireContext(),this) }
    override val sortText: TextView=binding.sort
    override val mapView: MapView=binding.searchMapView
    override val image: Image<ProductModel>
        get() = {it.images[0].file}
    override val location: Location<ProductModel>
        get() = {LatLng(it.shop.lat,it.shop.lng)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@SearchFragment)
        }
        initAdapter()
        buttonInstall()
        binding.apply {
            location.setOnClickListener{
                this@SearchFragment.myLocation?.let {
                    myLocation()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val result = SearchFragmentArgs.fromBundle(requireArguments()).filter
        if (result != null) searchWithFilters(result)
    }
    private fun initAdapter() {
        binding.recyclerView.apply { layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
        myAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh == LoadState.Loading)
                binding.progressBar8.visibility = View.VISIBLE
            else binding.progressBar8.visibility = View.GONE
        }
    }
    private fun buttonInstall() {
        binding.apply { filter.click { viewModel.apply {
                    val result = DataModel(city = city.value, flag = "product")
                    setFragmentResult("new_key", bundleOf("info" to result))
                    navigateToFilter()
                }
            }

        }
    }

    override fun loadSelections(list: List<SelectionModel>) {
        val events= getClickListener(list)
        myAdapter.apply {
            onCallItemClickListener = { events.onClickCall(it) }
            onItemClickListener = { events.onClickItem(it) }
            onLikeItemClickListener = { events.onClickLike(it) }
            onMessageItemClickListener = { events.onClickMessage(it) }
            onAnotherItemClickListener ={events.onClickAnother(it)} }
    }

    override fun showBottomScreen(
        context: Context,
        item: ProductModel,
        list: List<SelectionModel>,
        onClickBottomFragment: OnClickBottomSheetFragment<ProductModel>,
        mean: Boolean
    ) {

        showBottomSheet(context,item,list,onClickBottomFragment,mean)
    }
    override suspend fun loadItems(items: PagingData<ProductModel>)= myAdapter.submitData(items)

    override fun saveCurrentLocation(lat: Double, long: Double) = viewModel.saveLocation(lat,long)
}


