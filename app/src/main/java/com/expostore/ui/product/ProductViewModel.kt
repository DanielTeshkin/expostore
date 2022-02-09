package com.expostore.ui.product

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R

class ProductViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var navController: NavController
    lateinit var id: String
    lateinit var phoneSeller: String
    lateinit var shopId: String
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

    fun navigateToShop(view: View){
        bundle.putString("shopId",shopId)
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_productFragment_to_shopFragment, bundle)
    }
}