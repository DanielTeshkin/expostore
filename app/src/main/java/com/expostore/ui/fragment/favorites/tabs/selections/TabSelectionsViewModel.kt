package com.expostore.ui.fragment.favorites.tabs.selections

import androidx.lifecycle.ViewModel
import com.expostore.api.response.CategoryResponse
import com.expostore.model.category.CategoryModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.favorites.FavoriteRepository
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import javax.inject.Inject

@HiltViewModel
class TabSelectionsViewModel @Inject constructor(private val repository: FavoriteRepository) : BaseViewModel() {
    private val _state= MutableSharedFlow<ResponseState<List<CategoryModel>>>()
    val state=_state.asSharedFlow()
    private val _delete= MutableSharedFlow<ResponseState<CategoryResponse>>()
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