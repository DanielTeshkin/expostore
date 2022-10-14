package com.expostore.ui.fragment.search.main

import android.content.Context
import android.content.Intent
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
import com.expostore.ui.fragment.category.OnClickListeners
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.main.adapter.ProductMarkerApi
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.expostore.ui.fragment.specifications.DataModel
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment() : BaseSearchFragment<SearchFragmentBinding, ProductModel, SelectFavoriteResponseData, FavoriteProduct>(SearchFragmentBinding::inflate) {
    override val viewModel: SearchViewModel by viewModels()
    private val myAdapter: ProductsAdapter by lazy { ProductsAdapter(requireContext(),this) }
    override val sortText: TextView
        get()=binding.sort
    override val mapView: MapView
        get()=binding.searchMapView
    override val image: Image<ProductModel>
        get() = {it.images[0].file}
    override val location: Location<ProductModel>
        get() = {LatLng(it.shop.lat,it.shop.lng)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@SearchFragment)
            location.setOnClickListener {
                this@SearchFragment.myLocation?.let {
                    myLocation()
                }
            }
        }
        viewModel.apply {
            subscribe(selections) { state -> handleState(state) { loadSelections(it) } }
            subscribe(comparison) { handleState(it) { snackbarOpen() } }
        }
        initAdapter()
        buttonInstall()
    }

    override fun onStart() {
        super.onStart()
        setClickListener(getClickListener(listOf()))
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
                    navigateToFilter() } } }
    }

    private fun loadSelections(list: List<SelectionModel>) {
        val events= getClickListener(list)
        setClickListener(events)
    }

    private fun setClickListener(events:OnClickListeners<ProductModel>)= myAdapter.apply {
            onCallItemClickListener = { events.onClickCall(it) }
            onItemClickListener = { events.onClickItem(it) }
            onLikeItemClickListener = { events.onClickLike(it) }
            onMessageItemClickListener = { events.onClickMessage(it) }
            onAnotherItemClickListener ={events.onClickAnother(it)} }



    override fun navigateToComparison() { viewModel.navigateToComparison() }
    override fun addToComparison(id: String) =viewModel.addToComparison(id)
    override fun createSelection(product: String) = viewModel.createSelectionIntent(product)
    override fun addToSelection(id: String, product: String)= viewModel.addToSelection(id,product)
    override fun showBottomScreen(
        context: Context,
        item: ProductModel,
        list: List<SelectionModel>?,
        onClickBottomFragment: OnClickBottomSheetFragment<ProductModel>,
        mean: Boolean
    ) { showBottomSheet(context,item,list,onClickBottomFragment,mean) }
    override suspend fun loadItems(items: PagingData<ProductModel>)= myAdapter.submitData(items)
    override fun saveCurrentLocation(lat: Double, long: Double) = viewModel.saveLocation(lat,long)
    override fun block(model: ProductModel) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("service.ibuyer@yandex.ru"))
        startActivity(Intent.createChooser(intent, "Пожаловатьcя на товар ${model.id}"))
    }
}


