package com.expostore.ui.shop.shopcreate

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.ShopCreateFragmentBinding
import com.expostore.ui.base.BaseFragment

class ShopCreateFragment :
    BaseFragment<ShopCreateFragmentBinding>(ShopCreateFragmentBinding::inflate) {

    private lateinit var shopCreateViewModel: ShopCreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shopCreateViewModel = ViewModelProvider(this)[ShopCreateViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopCreateViewModel.btnSave = binding.btnSave
    }
}