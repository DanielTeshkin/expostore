package com.expostore.ui.fragment.category.personal


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.databinding.DetailPersonalSelectionFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseProductFragment
import com.expostore.ui.controllers.PersonalSelectionController


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPersonalSelectionFragment :
    BaseProductFragment<DetailPersonalSelectionFragmentBinding>(DetailPersonalSelectionFragmentBinding::inflate){
    override val viewModel:DetailPersonalSelectionViewModel by viewModels()
    override val intoPersonalSelection: Boolean
        get() = true

    private val controller: PersonalSelectionController by lazy { PersonalSelectionController(requireContext(),binding,
        {viewModel.deleteSelection()},{
        viewModel.navigateToEdit()})
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
            subscribe(navigation) { navigateSafety(it) }
            subscribe(selections) { state -> handleState(state){controller.setEvent(getClickListener(it))} }
        }
    }

    override fun deleteFromSelection(model: ProductModel) {
        viewModel.delete(model)
    }


    }