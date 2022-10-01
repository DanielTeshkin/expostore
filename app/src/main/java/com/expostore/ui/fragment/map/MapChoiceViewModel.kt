package com.expostore.ui.fragment.map

import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.google.android.gms.maps.model.LatLng


import kotlinx.coroutines.flow.MutableStateFlow

class MapChoiceViewModel : BaseViewModel() {

    val lat= MutableStateFlow(0.0)
    val lng= MutableStateFlow(0.0)
    val location=MutableStateFlow(LatLng(0.0,0.0))
    val distance= MutableStateFlow(1.0)


    fun saveLocation(latLng: LatLng){
       location.value=latLng
    }
    fun navigate()=navigationTo(MapChoiceFragmentDirections.actionMapChoiceToSearchFragment(
        FilterModel(lat = location.value.latitude, long = location.value.longitude, distance = distance.value)
    ))
    override fun onStart() {
        TODO("Not yet implemented")
    }
}