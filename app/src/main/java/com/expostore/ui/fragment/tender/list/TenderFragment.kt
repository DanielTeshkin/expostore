package com.expostore.ui.fragment.tender.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData

import com.expostore.databinding.TenderListFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.search.fragments.BaseSearchFragment
import com.expostore.ui.base.search.fragments.Image
import com.expostore.ui.base.search.fragments.Location
import com.expostore.ui.general.other.showBottomSheetTender
import com.expostore.ui.fragment.specifications.DataModel
import com.expostore.ui.fragment.tender.list.adapter.TenderAdapter
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TenderFragment :
    BaseSearchFragment<TenderListFragmentBinding, TenderModel, SelectFavoriteTenderResponseData, FavoriteTender>(TenderListFragmentBinding::inflate){

    override val viewModel: TenderListViewModel by  viewModels()
    override val sortText: TextView = binding.newFilter
    private lateinit var myAdapter: TenderAdapter
    override val mapView: MapView
        get() = binding.searchMapView
    override val image: Image<TenderModel>
    get()={ it.images?.get(0)?.file?:"" }
    override val location: Location<TenderModel>
        get() = { LatLng(it.lat?:0.0,it.long?:0.0) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter= TenderAdapter()
        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@TenderFragment)
        }

        binding.rvTenderList.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
        myAdapter.addLoadStateListener { loadState->
            if (loadState.refresh== LoadState.Loading)
                binding.progressBar9.visibility=View.VISIBLE
            else binding.progressBar9.visibility=View.GONE
        }
 binding.createTender.click { viewModel.createTender() }
        binding.apply {
            location.setOnClickListener{
                this@TenderFragment.myLocation?.let {
                    myLocation()
                }
            }
            filter.setOnClickListener {
                val result= DataModel(city = "Москва", flag  ="tender")
                setFragmentResult("new_key", bundleOf("info" to result))
                viewModel.navigateToFilter()
            } } }
    override fun onStart() {
        super.onStart()

        val filters=TenderListFragmentArgs.fromBundle(requireArguments()).filter
        if (filters!=null) searchWithFilters(filters) }


    override fun loadSelections(list: List<SelectionModel>) {
           val click = getClickListener(list)
        myAdapter.onCallItemClickListener={navigateToCall(it)}
        myAdapter.onMessageItemClickListener={ model->
           click.onClickMessage(model)
        }
        myAdapter.onItemClickListener={
            viewModel.navigateToItem(it)
        }
        myAdapter.onLikeItemClickListener={
            viewModel.updateSelected(it)
        }
        myAdapter.onAnotherItemClickListener={click.onClickAnother(it)}

    }

    override fun showBottomScreen(
        context: Context,
        item: TenderModel,
        list: List<SelectionModel>,
        onClickBottomFragment: OnClickBottomSheetFragment<TenderModel>,
        mean: Boolean
    ) {
        showBottomSheetTender(context,item,onClickBottomFragment)
    }

    override fun saveCurrentLocation(lat: Double, long: Double) {
       Log.i("msg","dff")
    }

    override suspend fun loadItems(items: PagingData<TenderModel>) {
       myAdapter.submitData(items)
    }


}