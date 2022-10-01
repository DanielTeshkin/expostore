package com.expostore.ui.fragment.product.qrcode

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var productQrCodeViewModel: ProductQrCodeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productQrCodeViewModel = ViewModelProvider(this).get(ProductQrCodeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("request_key"){_,bundle->
            val result=bundle.getString("image")
            Glide.with(this).load(result).into(binding.ivQrCode)
        }
        binding.download.click {  FileStorage(requireContext()).saveImage(binding.ivQrCode.drawable.toBitmap()) }


    }
}