package com.expostore.ui.fragment.product.personal

import androidx.lifecycle.ViewModel
import com.expostore.model.product.ProductModel

import com.expostore.ui.base.vms.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class ProductPersonalViewModel : BaseViewModel() {

    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun navigateToBack() = navigationTo(ProductPersonalFragmentDirections.actionPersonalProductfragmentToFavoritesFragment())

    fun navigateToNote(product:ProductModel)=navigationTo(ProductPersonalFragmentDirections.actionPersonalProductfragmentToNoteFragment(
        id=product.id,
        text = product.elected?.notes,flag = "product", isLiked = product.isLiked, flagNavigation = "product")
    )
}