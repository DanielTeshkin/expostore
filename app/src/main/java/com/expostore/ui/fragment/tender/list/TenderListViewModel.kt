package com.expostore.ui.fragment.tender.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.tender.TenderInteractor

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TenderListViewModel @Inject constructor( private val interactor: TenderInteractor) : BaseViewModel() {
       private val _tenders=MutableSharedFlow<ResponseState<List<TenderModel>>>()
        val tenders=_tenders.asSharedFlow()
    private val _state =MutableStateFlow<Boolean>(true)
    val state=_state.asStateFlow()
    private val _sort=MutableStateFlow<String?>(null)
    val sort=_sort.asStateFlow()
    private val token=MutableStateFlow<String?>(null)
    private val select = MutableSharedFlow<ResponseState<SelectFavoriteTenderResponseData>>()
    private val _chat=MutableSharedFlow<ResponseState<MainChat>>()
    val chat=_chat.asSharedFlow()
    fun createTender(){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToTenderCreateFragment())
    }

    fun selectFavorite(id: String) = interactor.selectFavorite(id).handleResult(select)
 val tender=interactor.letTenderFlow().cachedIn(viewModelScope)
    fun newSort(){
        if(_sort.value==null){
        _sort.value="-date_created"}
        else{ _sort.value=null}
    }
    fun navigateToItem(tenderModel: TenderModel){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToTenderItem(tenderModel))
    }

   private fun navigateToOpen(){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToOpenFragment())
    }
    fun createChat(tender:String) = interactor.chatCreate(tender).handleResult({
             Log.i("app","ff")
    },{ mainChat ->
        Log.i("chat", mainChat.itemsChat.map { it.tender?.images?.size }.joinToString())
        Log.i("chat1", mainChat.itemsChat.joinToString { it.messages?.size.toString() })
        val result = InfoItemChat(
            mainChat.identify()[1],
            mainChat.identify()[0],
            mainChat.chatsId(),
            mainChat.imagesProduct(),
            mainChat.productsName(), mainChat.identify()[3]

        )
        navigateToChat(result)
    },{Log.i("error",it.message.toString())

    })
    fun navigateToChat(result: InfoItemChat) {
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToChatFragment(result))
    }

    fun changeState(state:Boolean){
        this._state.value=state
    }
    fun loadTenderList() = interactor.letTenderFlow().cachedIn(viewModelScope)

    fun loadTenderListWithFilters(filterModel: FilterModel) = interactor.letTenderFlow(filterModel = filterModel).cachedIn(viewModelScope)

     fun navigateToFilter(){
         navigationTo(TenderListFragmentDirections.actionTenderListFragmentToSearchFilterFragment())
     }

        fun check(){
            if(token.value.isNullOrEmpty())navigateToOpen()
        }
    fun navigateToNote(model:TenderModel){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "tender", flagNavigation = "tender")) }


    override fun onStart() {

    }
}