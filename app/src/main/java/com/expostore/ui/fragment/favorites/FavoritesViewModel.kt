package com.expostore.ui.fragment.favorites

import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(private val interactor: FavoritesInteractor) : BaseViewModel() {
    private val _state= MutableSharedFlow<ResponseState<List<SelectionModel>>>()
    val state=_state.asSharedFlow()
    override fun onStart() {

    }

   fun loadList()= interactor.userSelectionList().handleResult(_state)


    fun navigateToSelection(selectionModel: SelectionModel) {
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToPersonalSelection(selectionModel))
    }
    fun navigateToCreateSelection()=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToSelectionCreate())
    fun navigateToSearch(typeSearch: String?) {
        if (typeSearch=="product")
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToSearchFragment())
        else navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToTenderListFragment())
    }


    fun navigateToComparison()=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToComparison())


}