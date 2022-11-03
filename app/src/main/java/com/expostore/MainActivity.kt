package com.expostore

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.data.repositories.SelectionRepository
import com.expostore.databinding.ActivityMainBinding
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.fragment.favorites.SelectionsSharedRepository
import com.expostore.ui.fragment.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var profileRepository: ProfileRepository

    @Inject
    lateinit var selectionRepository: SelectionRepository

    @Inject
    lateinit var categoryRepository: CategoryRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navigation = binding.bottomNavigationView
        navigation.setupWithNavController(navController)
      //  val infoChat=intent.getParcelableExtra<InfoItemChat>("info_chat")
      //  if(infoChat!=null) navController.navigate(MainFragmentDirections.actionMainFragmentToChatFragment(infoChat))


    }


    fun setVisibleBottomNavView(isVisible: Boolean) {
        if(binding.bottomNavigationView.isVisible != isVisible) {
            animate()
            binding.bottomNavigationView.isVisible = isVisible
        }
    }



    private fun animate(){
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration=300
        transition.addTarget(  binding.bottomNavigationView)
        TransitionManager.beginDelayedTransition(  binding.bottomNavigationView, transition)
    }



}