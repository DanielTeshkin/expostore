package com.expostore.ui.fragment.chats.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.api.pojo.getchats.ResponseFile
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.model.chats.DataMapping.ItemChat
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.general.ChatRepository
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

    private lateinit var update:Job
   private val _item=MutableSharedFlow<ResponseState<ItemChat>>()
    val item=_item.asSharedFlow()
    private val _message=MutableSharedFlow<ResponseState<MessageRequest>>()
          val  message=_message.asSharedFlow()
    private val _instanceAdapter= MutableLiveData(false)
    val instanceAdapter=_instanceAdapter
    private val _instanceProgressBar= MutableLiveData(true)
    val instanceProgressBar=_instanceProgressBar
    private val _save=MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    val save=_save.asSharedFlow()
    private val _saveFile=MutableSharedFlow<ResponseState<ResponseFile>>()
    val saveFile=_saveFile.asSharedFlow()
    override fun onStart() {
    }
    fun updateMessages(id:String){
        update=viewModelScope.repeat(10000){
                      chatRepository.chatItem(id).handleResult(_item) } }

    fun sentMessageOrUpdate(id: String,body:  MessageRequest){
          chatRepository.postMessage(id,body).handleResult(_message)

    }


    fun changeAdapterInstance(){
             _instanceAdapter.value=true
     }

    fun changeProgressBarInstance() {
        viewModelScope.launch {

        _instanceProgressBar.value = false
    }
    }



    fun saveMessage(requestData: SaveImageRequestData){
        chatRepository.saveImage(requestData).handleResult(_save)
    }

    fun stopUpdate() {
        viewModelScope.launch {
            update.join()
        }
    }

}




