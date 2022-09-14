package com.expostore.ui.fragment.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.BaseProductListFragment
import com.expostore.ui.controllers.DetailCategoryController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
 class DetailCategoryFragment :
    BaseProductListFragment<DetailCategoryFragmentBinding>(DetailCategoryFragmentBinding::inflate) {
    override val viewModel:DetailCategoryViewModel by viewModels()
    private val controller : DetailCategoryController by lazy {
        DetailCategoryController(binding = binding, context = requireContext())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val result = DetailCategoryFragmentArgs.fromBundle(requireArguments()).selection
        viewModel.apply {
            saveSelection(result)
            subscribe(selectionModel) { controller.showUI(it) }
        }
    }

    override fun loadSelections(list: List<SelectionModel>) {
        controller.setEvent(getClickListener(list))
    }


}