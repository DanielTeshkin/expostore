package com.expostore.ui.controllers

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.getshop.GetShopResponseData
import com.expostore.databinding.ShopFragmentBinding
import com.expostore.extension.load
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import com.expostore.ui.fragment.category.ProductSelectionAdapter

class ShopPageController(private val binding:ShopFragmentBinding):BaseProductController() {
     fun setupInfoShop(data: GetShopResponseData) {
        binding.apply {
            tvShopName.text = data.name
            tvShopAddress.text = data.address
            tvShopShoppingCenter.text = data.shoppingCenter
            ivAvatar.load(data.image)
            ivBackground.load(data.image)
            products.addAll(data.products.map { it.toModel })
            rvShopProducts.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter
            }
        }
    }

}