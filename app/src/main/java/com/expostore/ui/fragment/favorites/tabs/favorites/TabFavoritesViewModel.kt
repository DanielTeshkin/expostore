package com.expostore.ui.fragment.favorites.tabs.favorites


import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.NoteRequest
import com.expostore.api.response.NoteResponse
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TabFavoritesViewModel @Inject constructor(private val interactor: FavoritesInteractor) : BaseViewModel() {
    private val _favoriteList= MutableSharedFlow<ResponseState<List<FavoriteProduct>>>()
   val favoriteList=_favoriteList.asSharedFlow()
    private val _delete=MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    val  delete=_delete.asSharedFlow()
    private val _state= MutableStateFlow<Boolean>(true)
    val state=_state.asStateFlow()
    private val flagNote= MutableStateFlow(false)
    private val _note=MutableSharedFlow<ResponseState<NoteResponse>>()


    fun loadFavoriteList(){
        interactor.getProductFavoriteList().handleResult(_favoriteList)
    }
    fun update(id:String){
        interactor.updateSelected(id).handleResult(_delete)
    }
    fun updateOrCreateNote(id:String,text:NoteRequest){

    }



    fun navigation(){
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment2())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}