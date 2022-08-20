package com.expostore.ui.fragment.tender.item

import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.tender.TenderInteractor

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class TenderItemViewModel @Inject constructor(private val interactor: TenderInteractor):BaseViewModel() {
    private val _chatUI= MutableSharedFlow<ResponseState<MainChat>>()
    val chatUI=_chatUI.asSharedFlow()
    private val _tender= MutableStateFlow(TenderModel())
    val tender=_tender.asStateFlow()

    fun createChat(tender:String) = interactor.chatCreate(tender).handleResult(_chatUI,{chat->
        val result = InfoItemChat(
            chat.identify()[1],
            chat.identify()[0],
            chat.chatsId(),
            chat.imagesProduct(),
            chat.productsName(), chat.identify()[3]
        )
        navigationTo(TenderItemFragmentDirections.actionTenderItemToChatFragment(result))
    })
    fun navigateToShop(){
        navigationTo(TenderItemFragmentDirections.actionTenderItemToShopFragment())
    }
    fun saveTender(item:TenderModel){
        _tender.value=item
    }
    override fun onStart() {
        TODO("Not yet implemented")
    }
}