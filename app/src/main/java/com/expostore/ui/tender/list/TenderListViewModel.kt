package com.expostore.ui.tender.list

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R

class TenderListViewModel : ViewModel() {
    lateinit var navController: NavController
    var search = ""

    fun createTender(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_tenderListFragment_to_tenderCreateFragment)
    }
}