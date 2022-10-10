package com.expostore.ui.fragment.favorites.tabs.tenders

import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.interactors.BaseItemsInteractor
import com.expostore.ui.base.interactors.BaseTenderInteractor
import com.expostore.ui.base.vms.BaseTenderViewModel
import com.expostore.ui.base.vms.BaseViewModel
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
class FavoriteTendersViewModel @Inject constructor(private val interactors: FavoritesInteractor,
                                                   override val interactor: BaseTenderInteractor
) : BaseTenderViewModel() {
    private val _tenders= MutableSharedFlow<ResponseState<List<FavoriteTender>>>()
    val tenders=_tenders.asSharedFlow()
    private val chatState= MutableSharedFlow<ResponseState<MainChat>>()
    override fun onStart() {

    }


   private fun getTenderFavoriteList()=interactors.getTenderFavoriteList().handleResult(_tenders)
    fun  updateSelectedTender(id:String)=interactors.updateSelectedTender(id).handleResult()



    override fun navigateToChat(infoItemChat: InfoItemChat)=navigationTo(FavoritesFragmentDirections
        .actionFavoritesFragmentToChatFragment(infoItemChat))

    override fun navigateToBlock() {
        TODO("Not yet implemented")
    }

    override fun navigateToItem(model: TenderModel) =navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToTenderItem(model))

    override fun navigateToNote(model: TenderModel) {
       navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToNoteFragment(id=model.id,
           isLiked = model.isLiked, text = model.elected?.notes, flag = "tender", flagNavigation = ""))
   }




}