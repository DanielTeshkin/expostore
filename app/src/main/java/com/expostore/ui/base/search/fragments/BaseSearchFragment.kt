package com.expostore.ui.base.search.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.R
import com.expostore.extension.toMarker
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.fragments.Inflate
import com.expostore.ui.base.search.BasePagingAdapter
import com.expostore.ui.base.search.DrawMarkerApi
import com.expostore.ui.base.search.InfoWindowAdapter
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

typealias Location<T> = (T)->LatLng
typealias Image<T> =(T)->String
abstract class BaseSearchFragment<Binding : ViewBinding,T:Any,E,A>(private val inflate: Inflate<Binding>) :
    BaseLocationFragment<Binding, T, E, A>(inflate),DrawMarkerApi<T>,GoogleMap.OnInfoWindowClickListener {
    private val markers = mutableMapOf<Marker, T>()
    abstract val sortText:TextView
    abstract val mapView:MapView
    abstract val location:Location<T>
    abstract val image:Image<T>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycleScope.launch{ viewModel.search().collect { loadItems(it) } }
        }
        popupMenuLoad()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
       mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onStop()
    }
    override fun onStop() {
        super.onStop()
       mapView.onStop()
    }
    fun searchWithFilters(filterModel: FilterModel){
        viewLifecycleOwner.lifecycleScope.launch {
        lifecycleScope.launch {
            viewModel.search(filterModel).collect {
                loadItems(it)
            }
        }
        }
    }
    private fun popupMenuLoad() {
        val popupMenu = PopupMenu(requireContext(), sortText)
        popupMenu.inflate(R.menu.sort_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.newList -> {
                    val sort = "date_created"
                    sortText.text = "По новизне"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.ratingList -> {
                    val sort = "avg"
                    sortText.text = "По рейтингу"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.priceList -> {
                    val sort = "price"
                    sortText.text = "По цене"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.popularList -> {
                    val sort = "count_views"
                    sortText.text = "По популярности"
                    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.publicList -> {
                    val sort = "end_date_of_publication"
                    sortText.text = "По дате публикации"
                    searchWithFilters(FilterModel(sort = sort))
                }

            }
            false
        }
        sortText.click {
            popupMenu.show()
        }
    }

    override fun block(model: T) {

    }
    override fun drawMarker(item: T) {
        val markerOptions = MarkerOptions().position(location.invoke(item))
            .icon(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_item
                )?.toMarker
            )
        googleMap.addMarker(markerOptions)?.let { markers[it]=item }
        googleMap.setInfoWindowAdapter(InfoWindowAdapter(image.invoke(item),requireContext()))

    }

    override fun onInfoWindowClick(marker: Marker) {
        markers[marker]?.let { viewModel.navigateToItem(it) }
    }



    abstract suspend fun loadItems(items:PagingData<T>)
}