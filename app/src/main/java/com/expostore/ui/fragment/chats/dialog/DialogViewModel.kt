package com.expostore.ui.fragment.chats.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.ChatRepository
import com.expostore.ui.fragment.chats.repeat
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class DialogViewModel @Inject constructor(private val chatRepository: ChatRepository) : BaseViewModel() {
   private val _item=MutableSharedFlow<ResponseState<ItemChatResponse>>()
    val item=_item.asSharedFlow()
    private val message=MutableSharedFlow<ResponseState<MessageRequest>>()
    private val _instanceAdapter= MutableLiveData(false)
    val instanceAdapter=_instanceAdapter
    private val _instanceProgressBar= MutableLiveData(true)
    val instanceProgressBar=_instanceProgressBar
    private val _save=MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    val save=_save.asSharedFlow()
    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun updateMessages(id:String){
                  viewModelScope.repeat(3000){
                      chatRepository.chatItem(id).handleResult(_item) }
    }

    fun sentMessageOrUpdate(id: String,body:  MessageRequest){
          chatRepository.postMessage(id,body).handleResult(message)
        chatRepository.chatItem(id).handleResult(_item)}


    fun changeAdapterInstance(){
             _instanceAdapter.value=true
     }

    fun changeProgressBarInstance() {
        viewModelScope.launch {
            delay(3000)
        _instanceProgressBar.value = false
    }
    }



}