package com.expostore.ui.fragment.favorites.tabs.favorites

import androidx.lifecycle.ViewModel
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.favorites.FavoriteRepository
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TabFavoritesViewModel @Inject constructor(private val repository: FavoriteRepository) : BaseViewModel() {
    private val _favoriteList= MutableSharedFlow<ResponseState<List<FavoriteProduct>>>()
   val favoriteList=_favoriteList.asSharedFlow()
    private val _delete=MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    val  delete=_delete.asSharedFlow()
    private val _state= MutableStateFlow<Boolean>(true)
    val state=_state.asStateFlow()

    fun loadFavoriteList(){
        repository.getFavoritesList().handleResult(_favoriteList)
    }
    fun update(id:String){
        repository.updateSelected(id).handleResult(_delete)
    }
    fun updateState(){
        when(_state.value) {
           true-> _state.value = false
            false->_state.value = true
        }
    }
    fun navigation(model:ProductModel){
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment2(model))
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}