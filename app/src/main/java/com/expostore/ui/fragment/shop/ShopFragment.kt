package com.expostore.ui.fragment.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ShopFragmentBinding
import com.expostore.extension.load
import com.expostore.extension.loadBanner
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.toModel
import com.expostore.ui.base.fragments.BaseProductFragment
import com.expostore.ui.controllers.ShopPageController
import com.expostore.ui.fragment.category.ProductSelectionAdapter
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.ui.fragment.chats.loadTabImage
import com.expostore.ui.fragment.shop.shopcreate.ShopCreateFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
 class ShopFragment : BaseProductFragment<ShopFragmentBinding>(ShopFragmentBinding::inflate) {
    override val viewModel: ShopViewModel by viewModels()
    val mAdapter: ProductSelectionAdapter by lazy {
        ProductSelectionAdapter(products)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val info=ShopFragmentArgs.fromBundle(requireArguments()).id
        viewModel.getShop(info)
        viewModel.apply {
            getSelections()
            subscribe(shop) { state -> handleState(state){ data->
                binding.apply {
                    tvShopName.text = data.name
                    tvShopAddress.text = data.address
                    tvShopShoppingCenter.text = data.shoppingCenter
                    ivAvatar.loadAvatar(data.image.file)
                    ivBackground.loadBanner(data.image.file)
                    products.addAll(data.products.map { it.toModel })
                    mAdapter.onClick = getClickListener(listOf())
                    rvShopProducts.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = mAdapter
                    }
                }
            }
            }

        } }

    override fun loadSelections(list: List<SelectionModel>) {
        mAdapter.onClick=getClickListener(list)
    }


}