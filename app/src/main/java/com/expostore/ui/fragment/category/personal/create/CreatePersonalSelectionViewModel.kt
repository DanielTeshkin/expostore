package com.expostore.ui.fragment.category.personal.create

import androidx.lifecycle.ViewModel
import com.expostore.api.request.SelectionRequest
import com.expostore.data.repositories.SelectionRepository
import com.expostore.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreatePersonalSelectionViewModel @Inject constructor(private val selectionRepository: SelectionRepository) : BaseViewModel() {
  private val product=MutableStateFlow<String>("")
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun saveProduct(id: String){
         product.value=id
    }

    fun createSelection(name:String){
        selectionRepository.createSelection(SelectionRequest(name, listOf(product.value))).handleResult({
            navigationTo(CreatePersonalSelectionFragmentDirections.actionSelectionCreateToSearchFragment())
        })
    }
}