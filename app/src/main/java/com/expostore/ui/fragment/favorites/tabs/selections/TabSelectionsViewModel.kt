package com.expostore.ui.fragment.favorites.tabs.selections

import android.text.Selection
import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.SelectionRepository
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import javax.inject.Inject

@HiltViewModel
class TabSelectionsViewModel @Inject constructor(private val repository: SelectionRepository) : BaseViewModel() {
    private val _state= MutableSharedFlow<ResponseState<List<SelectionModel>>>()
    val state=_state.asSharedFlow()
    private val _delete= MutableSharedFlow<ResponseState<SelectionModel>>()
    val delete=_delete.asSharedFlow()
    fun loadList(){
             repository.userSelectionList().handleResult(_state)
         }
    fun delete(id:String){
        repository.deleteUserSelection(id).handleResult(_delete)
    }

    fun navigate(){
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToDetailCategoryFragment2())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

}