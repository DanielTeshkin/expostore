package com.expostore.ui.fragment.product.personal

import androidx.lifecycle.ViewModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseProductInteractor

import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.product.ProductInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductPersonalViewModel @Inject constructor(private val interactor: BaseProductInteractor) : BaseViewModel() {
    private val _product= MutableStateFlow(ProductModel())
    val product=_product.asStateFlow()
    override fun onStart() {

    }

    fun getProduct(id:String?) {
        if (!id.isNullOrEmpty()) interactor.getPersonalProduct(id).handleResult({}, { saveProduct(it) })
    }

    fun saveProduct(model:ProductModel){ _product.value=model }
    fun navigateToBack()=navController.popBackStack()

    fun navigateToNote(product:ProductModel)=navigationTo(ProductPersonalFragmentDirections.actionPersonalProductfragmentToNoteFragment(
        id=product.id,
        text = product.elected?.notes,flag = "product", isLiked = product.isLiked, flagNavigation = "personal")
    )
}