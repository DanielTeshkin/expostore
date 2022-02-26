package com.expostore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
//
//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.mainFragment,
//            R.id.searchFragment,
//            R.id.favoritesFragment,
//            R.id.tenderListFragment,
//            R.id.chatFragment)
//            .build()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}