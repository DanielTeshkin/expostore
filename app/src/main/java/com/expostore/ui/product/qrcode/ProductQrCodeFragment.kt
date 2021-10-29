package com.expostore.ui.product.qrcode

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.R
import com.expostore.databinding.ProductQrCodeFragmentBinding

class ProductQrCodeFragment : Fragment() {

    private lateinit var binding: ProductQrCodeFragmentBinding
    private lateinit var productQrCodeViewModel: ProductQrCodeViewModel
    //var id: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_qr_code_fragment, container, false)
        productQrCodeViewModel = ViewModelProvider(this).get(ProductQrCodeViewModel::class.java)
        binding.productQrCodeVM = productQrCodeViewModel
        //id = arguments?.getString("id")
        //id?.let { productViewModel.id = it }
        return binding.root
    }
}