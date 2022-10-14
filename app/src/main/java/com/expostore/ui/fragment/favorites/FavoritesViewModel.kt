package com.expostore.ui.fragment.favorites

import android.util.Log
import com.expostore.data.repositories.ProfileRepository
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
class FavoritesViewModel @Inject constructor(private val users:ProfileRepository) : BaseViewModel() {
    private val _state= MutableSharedFlow<ResponseState<List<SelectionModel>>>()
    val state=_state.asSharedFlow()
    override fun onStart() {

    }
     fun check(){
         val token=if(!users.getToken().isNullOrEmpty())users.getToken() else ""
         val state= token!!.isEmpty()
         Log.i("state",state.toString())
         if(state)navigateToOpen()
     }

    fun navigateToSelection(selectionModel: SelectionModel) {
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToPersonalSelection(selectionModel))
    }
    fun navigateToCreateSelection()=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToSelectionCreate())
    fun navigateToOpen()=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToOpenFragment())

    fun navigateToComparison()=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToComparison())


}