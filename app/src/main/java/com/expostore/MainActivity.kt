package com.expostore

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.expostore.data.repositories.ProfileRepository
import com.expostore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var profileRepository: ProfileRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val token=profileRepository.getToken()
        val navigation=binding.bottomNavigationView
       navigation.setupWithNavController(navController)




    }
    fun setVisibleBottomNavView(isVisible: Boolean) {
        if(binding.bottomNavigationView.isVisible != isVisible) {
            binding.bottomNavigationView.isVisible = isVisible
        }
    }



}