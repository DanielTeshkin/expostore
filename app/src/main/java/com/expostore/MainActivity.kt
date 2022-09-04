package com.expostore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.expostore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navigation=binding.bottomNavigationView
       navigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatFragment -> navigation.visibility = View.GONE
                    R.id.productFragment->navigation.visibility = View.GONE
                    R.id.profileFragment->navigation.visibility = View.GONE
                    R.id.EditProfileFragment ->navigation.visibility = View.GONE
                    R.id.shop_create->navigation.visibility = View.GONE
                    R.id.myProductsFragment->navigation.visibility = View.GONE
                    R.id.my_tenders->navigation.visibility = View.GONE
                    R.id.webViewFragment ->navigation.visibility = View.GONE
                    R.id.reviewsFragment ->navigation.visibility = View.GONE
                    R.id.support_fragment ->navigation.visibility = View.GONE
                    R.id.edit_my_product->navigation.visibility = View.GONE
                    else -> {
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }

            }

        }



    }
    fun setVisibleBottomNavView(isVisible: Boolean) {
        if(binding.bottomNavigationView.isVisible != isVisible) {
            binding.bottomNavigationView.isVisible = isVisible
        }
    }

}