package com.expostore.ui.fragment.category.personal.create


import com.expostore.data.remote.api.request.SelectionRequest
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.data.repositories.SelectionRepository
import com.expostore.model.category.toModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreatePersonalSelectionViewModel @Inject constructor(private val selectionRepository: SelectionRepository) : BaseViewModel() {
  private val product=MutableStateFlow<String?>(null)
    private val state=MutableSharedFlow<ResponseState<SelectionResponse>>()

    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun saveProduct(id: String){
         product.value=id
    }

    fun update(id: String, request: SelectionRequest)=selectionRepository.updateSelection(
        request,id
    ).handleResult(state,{
        navigationTo(CreatePersonalSelectionFragmentDirections.actionSelectionCreateToPersonalSelection(it.toModel))
    })


    fun createSelection(name:String){
        if (product.value!=null){
        selectionRepository.createSelection(SelectionRequest(name, listOf(product.value!!))).handleResult(state,{
            navigationTo(CreatePersonalSelectionFragmentDirections.actionSelectionCreateToPersonalSelection(it.toModel))
        })
        }
        else
            selectionRepository.createSelection(SelectionRequest(name, listOf())).handleResult(state,{
                navigationTo(CreatePersonalSelectionFragmentDirections.actionSelectionCreateToPersonalSelection(it.toModel))
            })
    }
}