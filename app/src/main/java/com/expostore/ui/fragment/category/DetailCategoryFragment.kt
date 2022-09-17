package com.expostore.ui.fragment.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.BaseProductListFragment
import com.expostore.ui.controllers.DetailCategoryController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
 class DetailCategoryFragment :
    BaseProductListFragment<DetailCategoryFragmentBinding>(DetailCategoryFragmentBinding::inflate) {
    override val viewModel:DetailCategoryViewModel by viewModels()
    private val mAdapter:ProductSelectionAdapter by lazy {
        ProductSelectionAdapter(products)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = DetailCategoryFragmentArgs.fromBundle(requireArguments()).selection

        binding.apply {
            tvCategoryName.text = model.name
            rvDetailProduct.apply {
               layoutManager = LinearLayoutManager(requireContext())
                products.addAll(model.products)
               adapter = mAdapter
            }
        }
    }
    override fun loadSelections(list: List<SelectionModel>) {
      mAdapter.onClick=getClickListener(list)
    }

    }