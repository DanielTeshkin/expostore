package com.expostore.ui.fragment.tender.list

import android.content.Context
import android.content.Intent
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
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.general.other.showBottomSheetTender
import com.expostore.ui.fragment.specifications.DataModel
import com.expostore.ui.fragment.tender.list.adapter.TenderAdapter
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TenderFragment() :
    BaseSearchFragment<TenderListFragmentBinding, TenderModel, SelectFavoriteTenderResponseData, FavoriteTender>(TenderListFragmentBinding::inflate){

    override val viewModel: TenderListViewModel by  viewModels()
    override val sortText: TextView get()  = binding.newFilter
    private val myAdapter: TenderAdapter by lazy  { TenderAdapter()}
    override val mapView: MapView
        get() = binding.searchMapView
    override val image: Image<TenderModel>
    get()={ it.images?.get(0)?.file?:"" }
    override val location: Location<TenderModel>
        get() = { LatLng(it.lat?:0.0,it.long?:0.0) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@TenderFragment)
            location.setOnClickListener{
                this@TenderFragment.myLocation?.let {
                    myLocation()
                }
            }
            filter.setOnClickListener {
                val result= DataModel(city = "Москва", flag  ="tender")
                setFragmentResult("new_key", bundleOf("info" to result))
                viewModel.navigateToFilter()
            }
            createTender.click { viewModel.createTender() }
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
         }
    override fun onStart() {
        super.onStart()
        clickInstall()
        val filters=TenderFragmentArgs.fromBundle(requireArguments()).filter
        if (filters!=null) {
            viewModel.saveFilter(filters)
            searchWithFilters()
        }
    }


     private fun clickInstall() {
           val click = getClickListener(listOf())
        myAdapter.apply {
            onCallItemClickListener = { navigateToCall(it) }
            onMessageItemClickListener = { model -> click.onClickMessage(model) }
            onItemClickListener = { viewModel.navigateToItem(it) }
            onLikeItemClickListener = { viewModel.updateSelected(it) }
            onAnotherItemClickListener = { click.onClickAnother(it) }
        }

    }

    override fun showBottomScreen(
        context: Context,
        item: TenderModel,
        list: List<SelectionModel>?,
        onClickBottomFragment: OnClickBottomSheetFragment<TenderModel>,
        mean: Boolean
    ) {
        showBottomSheetTender(context,item,onClickBottomFragment)
    }

    override fun block(model: TenderModel) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("service.ibuyer@yandex.ru"))
        startActivity(Intent.createChooser(intent, "Пожаловатьcя на тендер ${model.id}"))
    }

    override fun saveCurrentLocation(lat: Double, long: Double) {
       Log.i("msg","dff")
    }

    override suspend fun loadItems(items: PagingData<TenderModel>) {
       myAdapter.submitData(items)
    }


}