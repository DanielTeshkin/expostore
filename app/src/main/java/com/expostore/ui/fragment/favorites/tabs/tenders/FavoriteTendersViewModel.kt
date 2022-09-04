package com.expostore.ui.fragment.favorites.tabs.tenders

import androidx.lifecycle.ViewModel
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteTendersViewModel @Inject constructor(private val interactor: FavoritesInteractor) : BaseViewModel() {
    private val _tenders= MutableSharedFlow<ResponseState<List<FavoriteTender>>>()
    val tenders=_tenders.asSharedFlow()
    private val chatState= MutableSharedFlow<ResponseState<MainChat>>()
    override fun onStart() {

    }

    init {
        getTenderFavoriteList()
    }

   fun getTenderFavoriteList()=interactor.getTenderFavoriteList().handleResult(_tenders)
    fun  updateSelectedTender(id:String)=interactor.updateSelectedTender(id).handleResult()
    fun createChat(id: String)=interactor.chatCreate(id,"tender").handleResult(chatState,{
        val result = InfoItemChat(
            it.identify()[1],
            it.identify()[0],
            it.chatsId(),
            it.imagesProduct(),
            it.productsName(), it.identify()[3]
        )
        navigateToChat(result)
    })

    private fun navigateToChat(infoItemChat: InfoItemChat)=navigationTo(FavoritesFragmentDirections
        .actionFavoritesFragmentToChatFragment(infoItemChat))

   fun navigateToNote(model: TenderModel) {
       navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToNoteFragment(id=model.id,
           isLiked = model.isLiked, text = model.elected?.notes, flag = "tender", flagNavigation = "tender"))
   }


    fun navigateToTenderItem(tenderModel: TenderModel)=navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToTenderItem(tenderModel))

}