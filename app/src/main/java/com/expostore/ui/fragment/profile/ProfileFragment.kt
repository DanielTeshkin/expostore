package com.expostore.ui.fragment.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.expostore.data.AppPreferences
import com.expostore.databinding.ProfileFragmentBinding
import com.expostore.extension.loadBanner
import com.expostore.model.profile.ProfileModel
import com.expostore.model.profile.name
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.chats.dialog.bottom.BottomSheetImage

import com.expostore.ui.fragment.profile.profile_edit.click

import com.expostore.ui.state.ResponseState
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate)
   {

    private val profileViewModel: ProfileViewModel by viewModels()
       override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.apply {
            loadProfile()
            subscribe(profile) { handleResult(it) }
            subscribe(navigation) { navigateSafety(it) }

        }
       subscribeUI()
       }





    private fun handleResult(state: ResponseState<ProfileModel>) {
        when (state) {
            is ResponseState.Loading->Log.i("fff","ff")
            is ResponseState.Success -> handleSuccess(state.item)
            is ResponseState.Error -> Toast.makeText(
                requireContext(),
                state.throwable.message,
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun handleSuccess(profileModel: ProfileModel) {
        saveData(profileModel)
        setClickListeners(profileModel)
    }

       private fun subscribeUI(){
           binding.apply {
               subscribe(profileViewModel.profileInfo){
                   Log.i("avatar",it.name())
                   val image=ivAvatar
                   image.loadAvatar(it.avatar?.file?:"")
                   tvNumber.text = it.username
                   tvName.text = it.name()
                   it.shop?.image?.let { it1 -> ivBackground.loadBanner(it1.file) }
               }

               profileViewModel.apply {
                   subscribe(title) { btnShop.text = it }
               }
           }
       }


    private fun saveData(profileModel: ProfileModel) {
        profileViewModel.apply {
            save(profileModel)
        }

    }

       private fun setClickListeners(profileModel: ProfileModel){
        val shop = profileModel.shop
           binding.apply {
               btnShop.click {
                   state {
                       profileViewModel.exist.collect {
                           val info = ShopInfoModel(shop!!.name, shop.shoppingCenter, shop.address,shop.floor_and_office_number, info = it,shop.phone)
                           setFragmentResult("requestKey", bundleOf("name" to info))
                           profileViewModel.navigateShop()
                       }
                   }
               }
               btnSettings.click { profileViewModel.navigateEditProfile() }
               btnMyReviews.click { profileViewModel.navigateReviews("shop") }
               btnWritedMyReviews.click { profileViewModel.navigateReviews("my") }
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
                   profileViewModel.apply {
                       removeToken()
                       navigateToLogin() }
               }
           }
    }

    private fun openGallery(tag: String) {
       profileViewModel.updateTag(tag)
       ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent -> startForProfileImageResult.launch(intent) }
        }




    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result  ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {

                    val fileUri = data?.data!!
                    profileViewModel.updateAvatar(requireContext(), fileUri)

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Вы вышли", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
