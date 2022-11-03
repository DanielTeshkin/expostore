package com.expostore.ui.fragment.favorites.tabs.personal

import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.base.vms.BaseProductViewModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import javax.inject.Inject

@HiltViewModel
class TabPersonalProductViewModel @Inject constructor(override val interactor:BaseProductInteractor) : BaseProductViewModel() {
   private val _ui= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val ui=_ui.asSharedFlow()

    override fun onStart() {

    }

    override fun navigateToCreateSelection(product: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToComparison() =navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToComparison())

    override fun navigateToChat(value: InfoItemChat) {
        TODO("Not yet implemented")
    }

    override fun navigateToBlock() {
        TODO("Not yet implemented")
    }

  override  fun navigateToNote(model: ProductModel) =navigationTo(FavoritesFragmentDirections
        .actionFavoritesFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected.notes, flag = "product", flagNavigation = ""))

    override fun navigateToOpen() {

    }

    fun navigateToAddPersonalProduct()=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToCreatePersonalProduct())
       fun deletePersonal(id:String)=interactor.deletePersonalProduct(id).handleResult()
       fun load()=   interactor.getPersonalProducts().handleResult(_ui)
       fun navigate(model: ProductModel) = navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToPersonalProductfragment(model))
}