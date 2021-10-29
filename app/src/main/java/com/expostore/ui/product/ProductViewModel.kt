package com.expostore.ui.product

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R

class ProductViewModel : ViewModel() {

    lateinit var navController: NavController
    lateinit var id: String
    var bundle = Bundle()

    fun navigateToReviews(view: View){
        bundle.putString("id",id)
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_productFragment_to_reviewsFragment,bundle)
    }

    fun navigateToAddReview(view: View){
        bundle.putString("id",id)
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_productFragment_to_addReviewFragment,bundle)
    }

    fun navigateBack(view: View){
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }

    fun navigateToQrCode(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_productFragment_to_productQrCodeFragment)
    }
}