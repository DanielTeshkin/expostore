package com.expostore.ui.fragment.tender.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.model.tender.TenderModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.interactors.BaseTenderInteractor
import com.expostore.ui.base.vms.BaseTenderViewModel
import com.expostore.ui.fragment.search.filter.models.FilterModel

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TenderListViewModel @Inject constructor(override val interactor: BaseTenderInteractor) : BaseTenderViewModel() {
       private val _tenders=MutableSharedFlow<ResponseState<List<TenderModel>>>()
        val tenders=_tenders.asSharedFlow()
    private val _state =MutableStateFlow<Boolean>(true)
    val state=_state.asStateFlow()
    private val _sort=MutableStateFlow<String?>(null)
    val sort=_sort.asStateFlow()
    private val token=MutableStateFlow<String?>(null)
    private val _chat=MutableSharedFlow<ResponseState<MainChat>>()
    val chat=_chat.asSharedFlow()

    fun createTender()=when(checkAuthorizationState()) {
        true->navigationTo(TenderFragmentDirections.actionTenderListFragmentToTenderCreateFragment())
        false->navigateToOpen()
    }

    override fun navigateToItem(model: TenderModel){
        navigationTo(TenderFragmentDirections.actionTenderListFragmentToTenderItem(model))
    }

   override fun navigateToOpen()= navigationTo(TenderFragmentDirections.actionTenderListFragmentToOpenFragment())


   override fun navigateToChat(value: InfoItemChat) {
        navigationTo(TenderFragmentDirections.actionTenderListFragmentToChatFragment(value))
    }

    override fun navigateToBlock() {
        TODO("Not yet implemented")
    }

    fun changeState(state:Boolean){
        this._state.value=state
    }
    fun navigateToFilter(){
         navigationTo(TenderFragmentDirections.actionTenderListFragmentToSearchFilterFragment())
     }

        fun check(){
            if(token.value.isNullOrEmpty())navigateToOpen()
        }
    override fun navigateToNote(model:TenderModel){
        navigationTo(TenderFragmentDirections.actionTenderListFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "tender", flagNavigation = "")) }


    override fun onStart() {

    }
}