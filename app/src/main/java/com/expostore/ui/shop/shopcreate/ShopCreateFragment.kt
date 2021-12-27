package com.expostore.ui.shop.shopcreate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.R
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.databinding.ShopCreateFragmentBinding
import com.expostore.ui.product.ProductViewModel
import com.google.android.gms.maps.GoogleMap

class ShopCreateFragment : Fragment() {

    private lateinit var binding: ShopCreateFragmentBinding
    private lateinit var shopCreateViewModel: ShopCreateViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.shop_create_fragment, container, false)
        shopCreateViewModel = ViewModelProvider(this)[ShopCreateViewModel::class.java]
        binding.shopCreateVM = shopCreateViewModel

        shopCreateViewModel.btnSave = binding.btnSave

        return binding.root
    }
}