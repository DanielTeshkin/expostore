package com.expostore.ui.fragment.tender.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.data.AppPreferences
import com.expostore.databinding.TenderListFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.utils.TenderRecyclerViewAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TenderListFragment :
    BaseFragment<TenderListFragmentBinding>(TenderListFragmentBinding::inflate),
    OnMapReadyCallback {

    private lateinit var tenderListViewModel: TenderListViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mAdapter: TenderRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tenderListViewModel = ViewModelProvider(this).get(TenderListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.createTender.setOnClickListener { tenderListViewModel.createTender(it) }

        binding.searchMapView.onCreate(savedInstanceState)
        binding.searchMapView.onResume()
        binding.searchMapView.getMapAsync(this)

        fetchLocation()

        val token = AppPreferences.getSharedPreferences(requireContext()).getString("token", "")
       /* val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.getTenders("Bearer $token").enqueue(object : Callback<ArrayList<Tender>> {
            override fun onResponse(
                call: Call<ArrayList<Tender>>,
                response: Response<ArrayList<Tender>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        mAdapter = TenderRecyclerViewAdapter(response.body()!!)
                        binding.rvTenderList.apply {
                            layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            adapter = mAdapter
                        }
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Tender>>, t: Throwable) {}
        })*/
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                //Toast.makeText(requireContext(), "${it.key} = ${it.value}", Toast.LENGTH_SHORT).show()
                fetchLocation()
            }
        }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestMultiplePermissions.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

            return
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    //Toast.makeText(requireContext(), "map" + it.latitude.toString() + "" + it.longitude, Toast.LENGTH_SHORT).show()
                    val latLng = LatLng(it.latitude, it.longitude)
                    val markerOptions = MarkerOptions().position(latLng)
                        .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_marker))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                    googleMap.addMarker(markerOptions)
                    binding.searchMapView.onResume()
                    binding.searchMapView.getMapAsync(this)
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
    }
}