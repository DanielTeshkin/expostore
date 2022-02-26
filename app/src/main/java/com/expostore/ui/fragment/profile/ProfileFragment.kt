package com.expostore.ui.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.ProfileFragmentBinding
import com.expostore.ui.base.BaseFragment

class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMyProducts.setOnClickListener {
            profileViewModel.navigateToMyProducts(it)
        }
    }
}