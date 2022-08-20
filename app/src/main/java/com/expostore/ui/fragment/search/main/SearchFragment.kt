package com.expostore.ui.fragment.search.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.R
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.extension.toMarker
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseLocationFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.note.NoteData
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import com.expostore.ui.fragment.specifications.DataModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseLocationFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var myAdapter: ProductsAdapter
    private var markerPosition: Marker? = null
    private val load:Show<List<SelectionModel>> by lazy { { selectionLoad(it) }}
    private val showMarkersProduct : Show<List<ProductModel>> by lazy { {installMarkerProducts(it) }}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         initUI()
         makeStartRequest()
         installSubscribes()
        binding.apply {
            searchMapView.onCreate(savedInstanceState)
            searchMapView.getMapAsync(this@SearchFragment)
        }
    }
    override fun onStart() {
        super.onStart()
        binding.searchMapView.onStart()
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<FilterModel>("filters")
            if (result!=null) searchWithFilters(result)

        }
    }
    override fun onResume() {
        super.onResume()
        binding.searchMapView.onResume()

    }

    override fun onPause() {
        super.onPause()
        binding.searchMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.searchMapView.onStop()
    }


    private fun initUI(){
        initAdapter()
        buttonInstall()
        popupMenuLoad()
    }
    private fun installSubscribes(){
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(selectionList){handleState(it,load)}
            subscribe(productsMarkerUI){ handleState(it,showMarkersProduct)}
        }
    }

    private fun makeStartRequest(){
        viewModel.apply {
            startSearch()
            getSelections()
            getBaseProducts()
        }
    }


    private fun initAdapter(){
        myAdapter= ProductsAdapter(requireContext())
        myAdapter.onCallItemClickListener={navigateToCall(it)}
        myAdapter.onItemClickListener={ openProductScreen(it) }
        myAdapter.onLikeItemClickListener= { viewModel.selectFavorite(it) }
        myAdapter.onMessageItemClickListener={ model-> createChat(model.id) }
        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
        myAdapter.addLoadStateListener { loadState->
            if (loadState.refresh== LoadState.Loading)
                binding.progressBar8.visibility=View.VISIBLE
            else binding.progressBar8.visibility=View.GONE
        }
    }

    private fun buttonInstall(){
        binding.apply {
            filter.click {
                 viewModel.apply {
                    val result= DataModel(city = city.value, flag = "product")
                    setFragmentResult("new_key", bundleOf("info" to result))
                    navigateToFilter()
                }
            }
            location.setOnClickListener {
                this@SearchFragment.myLocation?.let {
                    myLocation()
                }
            }
        }
    }

    private fun popupMenuLoad(){
        val popupMenu=PopupMenu(requireContext(),binding.sort)
        popupMenu.inflate(R.menu.sort_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.newList ->{
                    val sort="date_created"
                    binding.sort.text = "По новизне"
                    searchWithFilters(FilterModel(sort=sort))
                }
                R.id.ratingList ->{
                    val sort="avg"
                    binding.sort.text ="По рейтингу"
                    searchWithFilters(FilterModel(sort=sort))
                }
                R.id.priceList ->{
                    val sort="price"
                    binding.sort.text = "По цене"
                    searchWithFilters(FilterModel(sort=sort))
                }
                R.id.popularList->{
                    val sort="count_views"
                    binding.sort.text = "По популярности"
                    searchWithFilters(FilterModel(sort=sort))
                }
                R.id.publicList->{
                    val sort="end_date_of_publication"
                    binding.sort.text = "По дате публикации"
                    searchWithFilters(FilterModel(sort=sort))
                }

            }
            false
        }
        binding.sort.click {
            popupMenu.show()
        }
    }


    fun createChat(id:String)= viewModel.createChat(id)

     private fun initPersonalSelectionCLick(): OnClickBottomSheetFragment {
       return object : OnClickBottomSheetFragment {
           override fun createSelection(product: String) {
               setFragmentResult("name", bundleOf("product" to product))
               viewModel.navigateToSelectionCreate()
           }

           override fun addToSelection(id: String, product: String) {
               viewModel.addToSelection(id, product)
           }

           override fun call(username: String) {
               navigateToCall(username)
           }

           override fun createNote(model: ProductModel) {
               setFragmentResult("dataNote", bundleOf("note" to NoteData(id=model.id,
               flag = "product", flagNavigation = "search", isLiked = model.isLiked) ))
            viewModel.navigateToNote()
           }

           override fun chatCreate(id: String) {
              createChat(id)
           }

           override fun share(id: String) {
               val sendIntent: Intent = Intent().apply {
                   action = Intent.ACTION_SEND
                   putExtra(Intent.EXTRA_TEXT, id)
                   type = "text/plain"
               }

               val shareIntent = Intent.createChooser(sendIntent, null)
               startActivity(shareIntent)
           }

           override fun block() {
               viewModel.navigateToBlock()
           }

       }
 }



 private fun openProductScreen(model: ProductModel){ viewModel.navigateToProduct(model) }

  private fun startSearch(){
      lifecycleScope.launch {
                viewModel.search
                    .collectLatest{ model ->
                        myAdapter.submitData(model)
                    }
            }

    }

   private fun searchWithFilters(result: FilterModel) {
       viewModel.apply {
           lifecycleScope.launch {
               searchWithFilters(result)
               .collectLatest {
                   myAdapter.submitData(it)
               }


           }
       }
    }

    private fun installMarkerProducts(list:List<ProductModel>){
         list.map {
             addProductMarker(it)
         }
    }
 private fun addProductMarker(it: ProductModel){
     val latLng=LatLng(it.shop.lat,it.shop.lng)
      googleMap.addMarker(
         MarkerOptions()
             .position(latLng)
             .icon(
                 bitmapDescriptorFromVector(
                     requireContext(),
                     it.images[0].file
                 )
             ).title(it.name)
     )
     googleMap.setOnMarkerClickListener(OnMarkerClickListener { _ -> // on marker click we are getting the title of our marker
         openProductScreen(it)
         false
     })


 }






     private fun selectionLoad(list:List<SelectionModel>?){
         myAdapter.onAnotherItemClickListener = { model ->
             showBottomSheet(requireContext(), model, list, initPersonalSelectionCLick())
         }
     }



    override fun onMapReady(map: GoogleMap) {
        Log.i("marker","aaa")
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
    }

    override fun onLocationChange(location: Location) {
       markerPosition?.position = LatLng(location.latitude, location.longitude)
        myLocation()
    }

    override fun onLocationFind(location: Location): Boolean {
        Log.i("loc",location.latitude.toString())
        if (!::googleMap.isInitialized) {
            return false
        }
        myLocation()
        return true
    }

    private fun myLocation() {
        myLocation?.let { location ->
            if (markerPosition != null) {
                moveToLocation(location)
               return@let
            }
            val latLng = LatLng(location.latitude, location.longitude)
            viewModel.apply {
              //  fetchLocationProduct(lat = location.latitude, long = location.latitude, name = null)
                saveLocation(location.latitude,location.longitude)
            }

            val markerOptions = MarkerOptions().position(latLng)
                .icon(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_marker
                    )?.toMarker
                )
            moveToLocation(location)
            googleMap.addMarker(markerOptions)?.let { markerPosition = it }
        }
    }

    private fun moveToLocation(location: Location, isAnimate: Boolean = true) {
        val latLng = LatLng(location.latitude, location.longitude)
        if (isAnimate) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        }
    }
    override fun noPermission() {
      /// viewModel.fetchProduct()
    }

    }

