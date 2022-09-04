package com.expostore.ui.fragment.favorites.tabs.personal

import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import javax.inject.Inject

@HiltViewModel
class TabPersonalProductViewModel @Inject constructor(private val interactor: FavoritesInteractor) : BaseViewModel() {
   private val _ui= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val ui=_ui.asSharedFlow()

    override fun onStart() {

    }


         fun navigateToAddPersonalProduct()=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToCreatePersonalProduct())
       fun deletePersonal(id:String)=interactor.deletePersonalProduct(id).handleResult()
       fun load()=   interactor.getPersonalProducts().handleResult(_ui)
       fun navigate(model: ProductModel) = navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToPersonalProductfragment(model))
}