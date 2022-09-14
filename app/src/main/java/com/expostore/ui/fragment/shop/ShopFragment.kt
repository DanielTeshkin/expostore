package com.expostore.ui.fragment.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.expostore.databinding.ShopFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.*
import com.expostore.ui.controllers.ShopPageController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
 class ShopFragment : BaseProductListFragment<ShopFragmentBinding>(ShopFragmentBinding::inflate) {
    override val viewModel: ShopViewModel by viewModels()
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
            subscribe(shop) { state -> handleState(state){controller.setupInfoShop(it)} }
        }
    }


    override fun loadSelections(list: List<SelectionModel>) {
        controller.setEvent(getClickListener(list))
    }


}