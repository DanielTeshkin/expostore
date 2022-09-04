package com.expostore.ui.fragment.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.expostore.data.remote.api.pojo.getshop.GetShopResponseData
import com.expostore.databinding.ShopFragmentBinding
import com.expostore.extension.load
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.BaseProductFragment
import com.expostore.ui.base.BaseProductViewModel
import com.expostore.ui.base.Show
import com.expostore.ui.controllers.ShopPageController
import com.expostore.ui.fragment.category.OnClickListener
import com.expostore.ui.fragment.category.ProductSelectionAdapter
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : BaseProductFragment<ShopFragmentBinding>(ShopFragmentBinding::inflate) {

    override val viewModel: ShopViewModel by viewModels()
    private val controller :ShopPageController by lazy { ShopPageController(binding) }


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
            subscribe(navigation){navigateSafety(it)}
            subscribe(selections) { state -> handleState(state){controller.setEvent(getClickListener(it))} }
        }
    }





}