package com.expostore.ui.controllers

import android.content.Context
import android.widget.PopupMenu
import com.expostore.R
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker

class SearchController(private val binding:SearchFragmentBinding, private val context: Context) :BaseProductController(),OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private val myAdapter: ProductsAdapter by lazy { ProductsAdapter(context) }
    private var markerPosition: Marker? = null
    private val markers = mutableMapOf<Marker, ProductModel>()


    fun onStartMap()=binding.searchMapView.onStart()
    fun onResumeMap()=binding.searchMapView.onStart()
    fun onPauseMap()=binding.searchMapView.onPause()
    fun onStopMap()=binding.searchMapView.onStop()







    private fun popupMenuLoad() {
        val popupMenu = PopupMenu(context, binding.sort)
        popupMenu.inflate(R.menu.sort_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.newList -> {
                    val sort = "date_created"
                    binding.sort.text = "По новизне"
                        //searchWithFilters(FilterModel(sort = sort))
                }
                R.id.ratingList -> {
                    val sort = "avg"
                    binding.sort.text = "По рейтингу"
                  //  searchWithFilters(FilterModel(sort = sort))
                }
                R.id.priceList -> {
                    val sort = "price"
                    binding.sort.text = "По цене"
                  //  searchWithFilters(FilterModel(sort = sort))
                }
                R.id.popularList -> {
                    val sort = "count_views"
                    binding.sort.text = "По популярности"
                //    searchWithFilters(FilterModel(sort = sort))
                }
                R.id.publicList -> {
                    val sort = "end_date_of_publication"
                    binding.sort.text = "По дате публикации"
                   // searchWithFilters(FilterModel(sort = sort))
                }

            }
            false
        }
        binding.sort.click {
            popupMenu.show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
    }


}