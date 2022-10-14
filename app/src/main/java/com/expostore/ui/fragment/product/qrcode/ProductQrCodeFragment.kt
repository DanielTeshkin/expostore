package com.expostore.ui.fragment.product.qrcode

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.expostore.databinding.ProductQrCodeFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductQrCodeFragment :
    BaseFragment<ProductQrCodeFragmentBinding>(ProductQrCodeFragmentBinding::inflate) {

    private  val productQrCodeViewModel: ProductQrCodeViewModel by viewModels()
    override var isBottomNavViewVisible: Boolean = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productQrCodeViewModel.apply {
            start(findNavController())
            subscribe(navigation){navigateSafety(it)}
        }
        setFragmentResultListener("request_key"){_,bundle->
            val result=bundle.getString("image")
            Glide.with(this).load(result).into(binding.ivQrCode)
        }
        binding.download.click {  FileStorage(requireContext()).saveImage(binding.ivQrCode.drawable.toBitmap()) }
        binding.btnBack.click{productQrCodeViewModel.navigateBack()}


    }
}