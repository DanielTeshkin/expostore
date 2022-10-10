package com.expostore.ui.base.vms

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.interactors.BaseItemsInteractor
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseItemViewModel<T : Any,A,E>:BaseViewModel() {

    protected abstract val interactor:BaseItemsInteractor<T,A,E>

    private val _chat= MutableSharedFlow<ResponseState<MainChat>>()
    private val chatData= MutableStateFlow(InfoItemChat())
    private val _favorites= MutableSharedFlow<ResponseState<List<E>>>()
    val favorites=_favorites.asSharedFlow()

    fun search(filterModel: FilterModel?=FilterModel())=interactor.search(filterModel = filterModel).cachedIn(viewModelScope)

    fun getFavorites()=interactor.getFavoriteList().handleResult(_favorites)
    fun createChat(id:String)=interactor.createChat(id).handleResult(_chat,{
        updateChatData(it)
        navigateToChat(chatData.value)
    })
    private fun updateChatData(chat: MainChat) = chat.apply {
        chatData.value = InfoItemChat(identify()[1],
            identify()[0],
            chatsId(),
            imagesProduct(),
            productsName(),
            identify()[3])
    }

    fun updateSelected(id:String)=interactor.updateSelected(id).handleResult()

    abstract fun navigateToChat(value: InfoItemChat)
    abstract fun navigateToBlock()
    abstract fun navigateToItem(model:T)
    abstract fun navigateToNote(model:T)

    override fun onStart() {
       Log.i("h","")
    }
}