package com.expostore.ui.controllers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseProductViewModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.category.OnClickListener
import com.expostore.ui.fragment.category.ProductSelectionAdapter
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import com.expostore.utils.OnClick
import com.expostore.utils.TenderCreateImageRecyclerViewAdapter

open class BaseProductController   {
    protected val products = mutableListOf<ProductModel>()
    protected val mAdapter: ProductSelectionAdapter by lazy {
        ProductSelectionAdapter(products)
    }

    fun setEvent(onClickListener: OnClickListener){
        mAdapter.onClick=onClickListener
    }

}