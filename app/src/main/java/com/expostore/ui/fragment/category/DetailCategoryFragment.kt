package com.expostore.ui.fragment.category


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.BaseProductFragment
import com.expostore.ui.base.Show
import com.expostore.ui.controllers.DetailCategoryController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCategoryFragment :
    BaseProductFragment<DetailCategoryFragmentBinding>(DetailCategoryFragmentBinding::inflate) {

    override val viewModel:DetailCategoryViewModel by viewModels()
    private val controller : DetailCategoryController by lazy {
        DetailCategoryController(binding = binding)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val result= DetailCategoryFragmentArgs.fromBundle(requireArguments()).selection
      viewModel.saveSelection(result)
        observeChangeState()
    }

    private fun observeChangeState() {
        val show:Show<List<SelectionModel>> ={controller.setEvent(getClickListener(it))}
        viewModel.apply {
            subscribe(selectionModel) { controller.showUI(it) }
            subscribe(navigation) { navigateSafety(it) }
            subscribe(selections) { handleState(it,show) }
        }
    }
    }