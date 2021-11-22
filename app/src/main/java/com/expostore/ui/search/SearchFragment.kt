package com.expostore.ui.search

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getlistproduct.GetListProductResponseData
import com.expostore.api.pojo.getlistproduct.Product
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.databinding.SearchFragmentBinding
import com.expostore.utils.CategoryRecyclerViewAdapter
import com.expostore.utils.OnClickRecyclerViewListener
import com.expostore.utils.SearchProductRecyclerViewAdapter
import com.expostore.utils.showKeyboard
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mAdapter: SearchProductRecyclerViewAdapter

    private var products = arrayListOf<Product>()

    private var firstClick = true

    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach {
            //Toast.makeText(requireContext(), "${it.key} = ${it.value}", Toast.LENGTH_SHORT).show()
            fetchLocation()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.searchVM = searchViewModel

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchMapView.onCreate(savedInstanceState)
        binding.searchMapView.onResume()
        binding.searchMapView.getMapAsync(this)



        fetchLocation()
        getProducts()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestMultiplePermissions.launch(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION))

            return
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    //Toast.makeText(requireContext(), "map" + it.latitude.toString() + "" + it.longitude, Toast.LENGTH_SHORT).show()
                    val latLng = LatLng(it.latitude, it.longitude)
                    val markerOptions = MarkerOptions().position(latLng).icon(bitmapDescriptorFromVector(requireContext(),R.drawable.ic_marker))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                    googleMap.addMarker(markerOptions)
                    binding.searchMapView.onResume()
                    binding.searchMapView.getMapAsync(this)
                }
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun setupRecyclerView(list: ArrayList<Product>) {
        mAdapter = SearchProductRecyclerViewAdapter(list, requireContext())
        binding.rvSearchProduct.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.onClick = searchOnClickListener()
        mAdapter.notifyDataSetChanged()
    }

    fun getProducts(){
        val token = (context as MainActivity).sharedPreferences.getString("token", "")

        if (token.isNullOrEmpty()){
            //Todo Подумать над нулевым токеном (возможно кидать на экран авторизации)
            //navController = Navigation.findNavController(binding.root)
            //navController.navigate(R.id.action_mainFragment_to_openFragment)
        }

        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

            serverApi.getListProduct().enqueue(object :
                Callback<GetListProductResponseData> {
                override fun onResponse(call: Call<GetListProductResponseData>, response: Response<GetListProductResponseData>) {
                    try {
                        if (response.isSuccessful) {
                            if (response.body() != null){
                                if (response.body()!!.results != null) {
                                    products = response.body()!!.results!!
                                    setupRecyclerView(products)

                                    binding.searchEt.addTextChangedListener(object : TextWatcher {
                                        override fun afterTextChanged(p0: Editable?) {}
                                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                                            val filteredList = arrayListOf<Product>()
                                            if (p0.toString() != "") {
                                                for (item in products) {
                                                    if (item.name.toLowerCase(Locale.ROOT).contains(p0.toString().toLowerCase(Locale.ROOT))) {
                                                        filteredList.add(item)
                                                    }
                                                }
                                                setupRecyclerView(filteredList)
                                            } else {
                                                setupRecyclerView(products)
                                            }
                                        }

                                    })
                                }
                            }
                        } else {
                            if (response.errorBody() != null) {
                                val jObjError = JSONObject(response.errorBody()!!.string())
                                val message = jObjError.getString("detail")
                                if (message.isNotEmpty())
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    catch (e: Exception){
                        Log.d("exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetListProductResponseData>, t: Throwable) {
                    Toast.makeText(context, (context as MainActivity).getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun searchOnClickListener() : OnClickRecyclerViewListener {
        return object : OnClickRecyclerViewListener {
            override fun onDetailCategoryButton(category: Category) {}
            override fun onProductClick(id: String?) {}

            override fun onLikeClick(like: Boolean, id: String?) {

                val token = (context as MainActivity).sharedPreferences.getString("token", "")

                if (token.isNullOrEmpty()){
                    //Todo Подумать над нулевым токеном (возможно кидать на экран авторизации)
                    //navController = Navigation.findNavController(binding.root)
                    //navController.navigate(R.id.action_mainFragment_to_openFragment)
                }

                val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

                if (!id.isNullOrEmpty())
                    serverApi.selectFavorite("Bearer $token", id).enqueue(object : Callback<SelectFavoriteResponseData> {
                        override fun onResponse(call: Call<SelectFavoriteResponseData>, response: Response<SelectFavoriteResponseData>) {
                            try {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Toвар добавлен в избранное",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    if (response.errorBody() != null) {
                                        val jObjError = JSONObject(response.errorBody()!!.string())
                                        val message = jObjError.getString("detail")
                                        if (message.isNotEmpty())
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            catch (e:Exception){
                                Log.d("exception", e.toString())
                            }
                        }

                        override fun onFailure(call: Call<SelectFavoriteResponseData>, t: Throwable) {
                            Toast.makeText(context, (context as MainActivity).getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
                        }
                    })
            }

            override fun onDetailCategoryProductItemClick(id: String?) {
                val bundle = Bundle()
                bundle.putString("id",id)
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.action_searchFragment_to_productFragment, bundle)
            }

            override fun onChatClick() {
                TODO("Not yet implemented")
            }
        }
    }




}