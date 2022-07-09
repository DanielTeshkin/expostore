package com.expostore.ui.fragment

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.extension.toMarker
import com.expostore.ui.fragment.search.main.SearchFragmentDirections
import com.expostore.ui.fragment.search.main.SearchViewModel
import com.expostore.ui.fragment.search.main.adapter.ProductsAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TestFragment : Fragment() {








}