package com.expostore.ui.fragment.profile

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.expostore.data.AppPreferences
import com.expostore.databinding.ProfileFragmentBinding
import com.expostore.extension.loadBanner
import com.expostore.model.profile.ProfileModel
import com.expostore.model.profile.name
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.chats.dialog.bottom.BottomSheetImage
import com.expostore.ui.fragment.chats.general.ImagePicker
import com.expostore.ui.fragment.profile.profile_edit.click

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate),
    BottomSheetImage.OnImagesSelectedListener {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.apply {
            loadProfile()
            subscribe(profile) { handleResult(it) }
            subscribe(navigation) { navigateSafety(it) }
        }

    }

    override fun onStart() {
        super.onStart()
        connectUI()

    }

    private fun handleResult(state: ResponseState<ProfileModel>) {
        when (state) {
            is ResponseState.Success -> handleSuccess(state.item)
            is ResponseState.Error -> Toast.makeText(
                requireContext(),
                state.throwable.message,
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun handleSuccess(profileModel: ProfileModel) {
        controllerData(profileModel)
    }

    private fun controllerData(profileModel: ProfileModel){
        saveData(profileModel)
        setClickListeners(profileModel)
    }

    private fun saveData(profileModel: ProfileModel) {
        profileViewModel.apply {
            save(profileModel)
        }

    }

    private fun connectUI() {
        binding.apply {
            val image=ivAvatar
            profileViewModel.apply {
                    state {
                    myAvatar.collect { image.loadAvatar(it) }}
                    state {
                    username.collect { tvNumber.text = it }}
                    state {
                    name.collect { tvName.text = it }}
                    state {
                    title.collect { btnShop.text = it }}
                    state {
                    shopAvatar.collect { ivBackground.loadBanner(it) } } }}

    }

   private fun setClickListeners(profileModel: ProfileModel){
        val shop = profileModel.shop
           binding.apply {
               btnShop.click {
                   state {
                       profileViewModel.exist.collect {
                           val info = ShopInfoModel(shop!!.name, shop.shoppingCenter, shop.address, it)
                           setFragmentResult("requestKey", bundleOf("name" to info))
                           profileViewModel.navigateShop()
                       }
                   }
               }
               btnSettings.click {
                   val result = InfoProfileModel(
                       profileModel.firstName!!, profileModel.lastName!!, profileModel.city!!,
                       profileModel.email!!
                   )
                   setFragmentResult("requestKey", bundleOf("info" to result))
                   profileViewModel.navigateEditProfile()
               }
               btnMyReviews.click {
                   val result = "shop"
                   setFragmentResult("requestKey", bundleOf("name" to result))
                   profileViewModel.navigateReviews()
               }
               btnWritedMyReviews.click {
                   val result = "my"
                   setFragmentResult("requestKey", bundleOf("name" to result))
                   profileViewModel.navigateReviews()
               }
               btnMyProducts.click {
                   val info =
                       shop?.name?.let { ShopInfoModel(it, shop.shoppingCenter, shop.address) }
                   setFragmentResult("requestKey", bundleOf("name" to info))
                   profileViewModel.navigateMyProduct()
               }
               btnMyTenders.click {
                   profileViewModel.navigateToTender()
               }
               ivAvatar.click {
                   openGallery("avatar")
               }
               if (profileModel.shop != null) {
                   ivBackground.click {
                       openGallery("banner")
                   }
               }
               btnSupport.click {
                   profileViewModel.navigationToSupport()
               }
               btnHelp.click {
                   profileViewModel.navigateToWeb()
               }
               logout.click {
                   AppPreferences
                       .getSharedPreferences(requireContext())
                       .edit().remove("token")
                       .remove("refresh")
                       .commit()
                   profileViewModel.navigateToLogin()

               }
           }
    }

    private fun openGallery(tag: String) {
            ImagePicker().bottomSheetSingleImageSetting().requestTag(tag).show(childFragmentManager)
        }

        override fun onImagesSelected(uris: MutableList<Uri>, tag: String?) {
            profileViewModel.updateAvatar(requireContext(), uris[0], tag)
        }

    }
