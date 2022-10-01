package com.expostore.ui.fragment.category.personal


import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.DetailPersonalSelectionFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.fragments.BaseProductFragment

import com.expostore.ui.fragment.category.ProductSelectionAdapter


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPersonalSelectionFragment :
    BaseProductFragment<DetailPersonalSelectionFragmentBinding>(DetailPersonalSelectionFragmentBinding::inflate){
    override val viewModel:DetailPersonalSelectionViewModel by viewModels()
    override val intoPersonalSelection: Boolean
        get() = true
    private val mAdapter: ProductSelectionAdapter by lazy { ProductSelectionAdapter(products) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selection=DetailPersonalSelectionFragmentArgs.fromBundle(requireArguments()).selection
        products.addAll(selection.products)
        binding.apply {
            tvCategoryName.text=selection.name
            rvDetailProduct.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter
            }
        }
         viewModel.save(selection)
         popMenuLoad()
    }
    private fun popMenuLoad(){
        val popupMenu= PopupMenu(context,binding.menu)
        popupMenu.inflate(R.menu.menu_selection)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> viewModel.navigateToEdit()
                R.id.delete -> viewModel.deleteSelection()
            }
            false
        }
        binding.menu.click { popupMenu.show() }
    }
    override fun deleteFromSelection(model: ProductModel) = viewModel.delete(model)
    override fun loadSelections(list: List<SelectionModel>) { mAdapter.onClick=getClickListener(list) }

    }