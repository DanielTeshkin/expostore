package com.expostore.ui.fragment.chats.fragment

import com.expostore.api.pojo.getchats.FileOrImageMessage
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.general.ChatRepository
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
@HiltViewModel
class ImageDialogView @Inject constructor( private val chatRepository: ChatRepository):BaseViewModel() {
    private val _message= MutableSharedFlow<ResponseState<MessageRequest>>()
    val  response=_message.asSharedFlow()
    private val _save=MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    val save=_save.asSharedFlow()
    fun sentMessageOrUpdate(id: String,body: MessageRequest){
    chatRepository.postMessage(id,body).handleResult(_message)}
    fun saveMessage(requestData: SaveImageRequestData){
        chatRepository.saveImage(requestData).handleResult(_save)
    }
    fun sendFileOrImage(id: String,body: FileOrImageMessage){
        chatRepository.sendFileOrImage(id, body).handleResult(_message)

    }
    override fun onStart() {
        TODO("Not yet implemented")
    }
}