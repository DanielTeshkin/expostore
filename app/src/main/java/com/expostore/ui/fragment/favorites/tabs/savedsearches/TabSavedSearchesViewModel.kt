package com.expostore.ui.fragment.favorites.tabs.savedsearches

import com.expostore.api.response.SaveSearchResponse
import com.expostore.model.SaveSearchModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class TabSavedSearchesViewModel @Inject constructor(private val repository: FavoriteRepository) : BaseViewModel() {
    private val _searchList=MutableSharedFlow<ResponseState<List<SaveSearchModel>>>()
    val searchList=_searchList.asSharedFlow()
    private val _saveSearch=MutableSharedFlow<ResponseState<SaveSearchResponse>>()
    val saveSearch=_saveSearch.asSharedFlow()

    fun loadList(){
              repository.saveSearchList().handleResult(_searchList)
    }

    fun deleteSaveSearch(id:String){
        repository.deleteSaveSearch(id).handleResult(_saveSearch)
    }
    fun navigate(){
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToSearchFilterFragment2())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}