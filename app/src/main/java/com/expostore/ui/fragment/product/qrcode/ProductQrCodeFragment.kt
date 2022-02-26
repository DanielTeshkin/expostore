package com.expostore.ui.fragment.product.qrcode

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.ProductQrCodeFragmentBinding
import com.expostore.ui.base.BaseFragment

class ProductQrCodeFragment :
    BaseFragment<ProductQrCodeFragmentBinding>(ProductQrCodeFragmentBinding::inflate) {

    private lateinit var productQrCodeViewModel: ProductQrCodeViewModel
    //var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productQrCodeViewModel = ViewModelProvider(this).get(ProductQrCodeViewModel::class.java)
    }
}