package com.expostore.ui.fragment.category.personal.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.data.remote.api.request.SelectionRequest
import com.expostore.databinding.CreatePersonalSelectionFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePersonalSelectionFragment : BaseFragment<CreatePersonalSelectionFragmentBinding>(CreatePersonalSelectionFragmentBinding::inflate) {
    private val viewModel:CreatePersonalSelectionViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(val update=CreatePersonalSelectionFragmentArgs.fromBundle(requireArguments()).selection){
            null->{initCreateBlock()}
            else->{initUpdateBlock(update)}
        }

    }
      fun initUpdateBlock(model:SelectionModel){
          binding.apply {
              searchEt.setText(model.name)
               title.text="Редактировать подборку"
              saving.apply {
                  text = "Сохранить"
                  click { viewModel.update(model.id,SelectionRequest(name=searchEt.text.toString(), products = model.products.map { it.id })) }
              }

          }


      }
    fun initCreateBlock(){
        val id=CreatePersonalSelectionFragmentArgs.fromBundle(requireArguments()).id
        if (id != null) {
            viewModel.saveProduct(id)
        }


        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }
        binding.saving.click {
            val text=binding.searchEt.text.toString()
            viewModel.createSelection(text)
        }
    }
}