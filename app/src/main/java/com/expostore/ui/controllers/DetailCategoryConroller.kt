package com.expostore.ui.controllers

import android.content.Context
import android.text.Selection
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.category.DetailCategoryViewModel
import com.expostore.ui.fragment.category.OnClickListener
import com.expostore.ui.fragment.category.ProductSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint


class DetailCategoryController(
    private val binding:DetailCategoryFragmentBinding):BaseProductController() {
    fun showUI(model: SelectionModel) {
        binding.apply {
            tvCategoryName.text=model.name
            rvDetailProduct.apply {
                layoutManager= LinearLayoutManager(context)
               products.addAll(model.products)
                adapter=mAdapter
            }
        }

    }

}