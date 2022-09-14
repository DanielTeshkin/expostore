package com.expostore.ui.fragment.category.personal


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.databinding.DetailPersonalSelectionFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseProductListFragment

import com.expostore.ui.controllers.PersonalSelectionController


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPersonalSelectionFragment :
    BaseProductListFragment<DetailPersonalSelectionFragmentBinding>(DetailPersonalSelectionFragmentBinding::inflate){
    override val viewModel:DetailPersonalSelectionViewModel by viewModels()
    override val intoPersonalSelection: Boolean
        get() = true

    private val controller: PersonalSelectionController by lazy { PersonalSelectionController(requireContext(),binding,
        {viewModel.navigateToEdit()},{
        viewModel.deleteSelection()})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selection=DetailPersonalSelectionFragmentArgs.fromBundle(requireArguments()).selection
        viewModel.save(selection)
        observeChangeState()
    }
    private fun observeChangeState() {
        viewModel.apply {
            subscribe(selection) { controller.showUI(it) }
        }
    }

    override fun deleteFromSelection(model: ProductModel) {
        viewModel.delete(model)
    }



    override fun loadSelections(list: List<SelectionModel>) {
        controller.setEvent(getClickListener(list))
    }


}