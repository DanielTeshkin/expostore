package com.expostore.ui.fragment.profile

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.databinding.ProfileFragmentBinding
import com.expostore.extension.load
import com.expostore.extension.loadBanner
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.base.BaseFragment

import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private  val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       profileViewModel.apply {
           subscribe(profile){handleResult(it)}
           subscribe(navigation){navigateSafety(it)}
       }
        binding.apply {
            btnShop.setOnClickListener {
                profileViewModel.navigateMyProduct(true)
            }
            ivAvatar.setOnClickListener {
               openGallery()
                }
            }
        }



    private fun handleResult(state: ResponseState<ProfileModel>) {
        when(state){
            is ResponseState.Success -> handleSuccess(state.item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccess(profileModel: ProfileModel) {
        profileViewModel.check(profileModel)
        binding.apply {
            tvName.text = profileModel.firstName + " " + profileModel.lastName
            tvNumber.text = profileModel.username
           ivAvatar.loadAvatar(profileModel)
            profileModel.shop?.image?.let { ivBackground.loadBanner(it.file) }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileViewModel.title.collect { btnShop.text = it }}
        }
        binding.btnShop.setOnClickListener {
            profileViewModel.navigateShop()
        }

        binding.btnSettings.setOnClickListener {
            Log.i("dir","no")
            profileViewModel.navigateEditProfile(
                profileModel.firstName!!, profileModel.lastName!!, profileModel.city!!,
                profileModel.email!!
            )
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_GET_CONTENT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

}

}