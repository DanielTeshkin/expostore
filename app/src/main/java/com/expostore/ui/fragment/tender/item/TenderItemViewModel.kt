package com.expostore.ui.fragment.tender.item

import android.util.Log
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.interactors.BaseTenderInteractor
import com.expostore.ui.base.vms.BaseTenderViewModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
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
class TenderItemViewModel @Inject constructor(override val interactor: BaseTenderInteractor):
    BaseTenderViewModel() {
    private val _chatUI= MutableSharedFlow<ResponseState<MainChat>>()
    val chatUI=_chatUI.asSharedFlow()
    private val _tender= MutableStateFlow(TenderModel())
    val tender=_tender.asStateFlow()

    override fun navigateToChat(value: InfoItemChat)=
        navigationTo(TenderItemFragmentDirections.actionTenderItemToChatFragment(value))

    override fun navigateToBlock() {
        TODO("Not yet implemented")
    }
    fun getTender(id:String?) = run { if (!id.isNullOrEmpty())interactor.getTender(id).handleResult({},{
        _tender.value=it
    }) }
    fun navigateToBack()=navController.popBackStack()


    override fun navigateToNote(model: TenderModel) {
        val model=tender.value
        navigationTo(TenderItemFragmentDirections.actionTenderItemToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "tender", flagNavigation = "tender"))
    }

    fun navigateToShop(){
        navigationTo(TenderItemFragmentDirections.actionTenderItemToShopFragment())
    }

    fun saveTender(item:TenderModel){
        _tender.value=item
    }


    override fun onStart() {
      Log.i("got","lod")
    }
}