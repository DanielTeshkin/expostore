package com.expostore.ui.fragment.profile.profile_edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.expostore.R
import com.expostore.databinding.EditProfileFragmentBinding
import com.expostore.ui.base.BaseFragment

class EditProfileFragment : BaseFragment<EditProfileFragmentBinding>(EditProfileFragmentBinding::inflate) {



    private val viewModel: EditProfileViewModel by viewModels()



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}