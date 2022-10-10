package com.expostore.ui.fragment.favorites.tabs.savedsearches

import android.util.Log
import com.expostore.data.remote.api.response.SaveSearchResponse
import com.expostore.model.SaveSearchModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.fragment.search.filter.models.FilterModel
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

    fun loadList(){
              interactor.getSearchList().handleResult(_searchList)
    }

    fun deleteSaveSearch(id:String){
        interactor.deleteSaveSearch(id).handleResult(_saveSearch)
    }
    fun navigate(model: SaveSearchModel){
        val result = FilterModel(
            name = model.params?.q,
            city = model.params?.city,
            price_min = model.body_params.price_min,
            price_max = model.body_params.price_max,
            lat = model.body_params.lat,
            long = model.body_params.long,
            characteristics = model.body_params.characteristics,
            sort = model.params?.sort,
            category = model.body_params.category
        )
        if (model.type_search=="product")
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToSearchFragment(result))
        else navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToTenderListFragment(result))
    }

    override fun onStart() {
        Log.i("","dd")
    }
}