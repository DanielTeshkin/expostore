package com.expostore.ui.fragment.favorites.tabs.favorites


import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.NoteRequest
import com.expostore.api.response.NoteResponse
import com.expostore.api.response.SelectionResponse
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.category.DetailCategoryFragmentDirections
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
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
    private val chat=MutableSharedFlow<ResponseState<MainChat>>()
   private val _selections= MutableStateFlow<List<SelectionModel>>(listOf())
    val selections=_selections.asStateFlow()
    private val selectionModel=MutableSharedFlow<ResponseState<SelectionResponse>>()


    fun loadFavoriteList(){ interactor.getProductFavoriteList().handleResult(_favoriteList) }
    fun update(id:String){ interactor.updateSelected(id).handleResult(_delete) }
    fun createChat(id:String)=interactor.chatCreate(id,"product").handleResult(chat,{
        val result = InfoItemChat(
            it.identify()[1],
            it.identify()[0],
            it.chatsId(),
            it.imagesProduct(),
            it.productsName(), it.identify()[3]
        )

        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToChatFragment(result))
    })
    fun addToSelection(id: String,product:String)=interactor.addToSelection(id, product).handleResult(selectionModel)
    fun delete(){
        onCleared()
    }
    fun selectionsSave(selectionsList:List<SelectionModel>){
        _selections.value=selectionsList
    }





    override fun onStart() {
        TODO("Not yet implemented")
    }
}