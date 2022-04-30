package com.expostore.ui.fragment.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.expostore.databinding.ProfileFragmentBinding
import com.expostore.extension.load
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       profileViewModel.apply {
           singleSubscribe(profile){handleResult(it)}
       }
        binding.apply {
            btnSettings.setOnClickListener {
                profileViewModel.navigateEditProfile()
            }
            btnShop.setOnClickListener {
                profileViewModel.navigateMyProduct(true)
            }
        }

    }

    private fun handleResult(state: ResponseState<ProfileModel>) {
        when(state){
            is ResponseState.Success -> handleSuccess(state.item)
        }
    }

    private fun handleSuccess(profileModel: ProfileModel) {
        profileViewModel.check(profileModel)
        binding.apply {
            tvName.text = profileModel.firstName + profileModel.lastName
            tvNumber.text = profileModel.username
            profileModel.avatar?.let { ivAvatar.loadAvatar(it) }
            profileModel.shop?.image?.let { ivBackground.load(it.file) }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileViewModel.title.collect { btnShop.text = it }}
        }

    }


}