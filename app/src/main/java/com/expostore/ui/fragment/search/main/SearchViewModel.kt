package com.expostore.ui.fragment.search.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.search.main.interactor.SearchInteractor
import com.expostore.ui.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val interactor: SearchInteractor) :
    BaseViewModel() {

    private val _uiState = MutableSharedFlow<SearchState>()
    val uiState = _uiState.asSharedFlow()

    override fun onStart() {
        /* no-op */
    }

    fun fetchProduct(): Flow<PagingData<ProductModel>> {
        return interactor.letProductFlow()
            .cachedIn(viewModelScope)
    }

    fun selectFavorite(id: String) {
        interactor.selectFavorite(id).handleResult (onSuccess = {
            emit(_uiState, SearchState.SelectFavorite(id, true))
        }) {
            emit(_uiState, SearchState.SelectFavorite(id, false))
        }
    }

}