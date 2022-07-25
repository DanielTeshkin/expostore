package com.expostore.ui.fragment.favorites.tabs.savedsearches

import com.expostore.api.response.SaveSearchResponse
import com.expostore.model.SaveSearchModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class TabSavedSearchesViewModel @Inject constructor(private val interactor: FavoritesInteractor) : BaseViewModel() {
    private val _searchList=MutableSharedFlow<ResponseState<List<SaveSearchModel>>>()
    val searchList=_searchList.asSharedFlow()
    private val _saveSearch=MutableSharedFlow<ResponseState<SaveSearchResponse>>()
    val saveSearch=_saveSearch.asSharedFlow()

    fun loadList(){
              interactor.getSearchList().handleResult(_searchList)
    }

    fun deleteSaveSearch(id:String){
        interactor.deleteSaveSearch(id).handleResult(_saveSearch)
    }
    fun navigate(){
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToSearchFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}