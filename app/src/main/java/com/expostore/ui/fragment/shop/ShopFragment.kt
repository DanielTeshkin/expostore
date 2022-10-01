package com.expostore.ui.fragment.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ShopFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.toModel
import com.expostore.ui.base.fragments.BaseProductFragment
import com.expostore.ui.controllers.ShopPageController
import com.expostore.ui.fragment.category.ProductSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
 class ShopFragment : BaseProductFragment<ShopFragmentBinding>(ShopFragmentBinding::inflate) {
    override val viewModel: ShopViewModel by viewModels()
    val mAdapter: ProductSelectionAdapter by lazy {
        ProductSelectionAdapter(products)
    }
     val manager=LinearLayoutManager(context)
    private val controller :ShopPageController by lazy { ShopPageController(binding,requireContext()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("shop") { _, bundle -> val info = bundle.getString("model")
            if (info != null) {
                viewModel.getShop(info)
            }
        }
        viewModel.apply {
            getSelections()
            subscribe(shop) { state -> handleState(state){ data->
                binding.apply {
                    tvShopName.text = data.name
                    tvShopAddress.text = data.address
                    tvShopShoppingCenter.text = data.shoppingCenter
                    ivAvatar.load(data.image.file)
                    ivBackground.load(data.image.file)
                    products.addAll(data.products.map { it.toModel })
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