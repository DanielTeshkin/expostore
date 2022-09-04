package com.expostore.ui.fragment.chats.fragment

import com.expostore.data.remote.api.pojo.getchats.FileOrImageMessage
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.getchats.ResponseFile
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class FileDialogViewModel@Inject constructor(private val chatRepository: ChatRepository, private val multimediaRepository: MultimediaRepository) : BaseViewModel() {
    private val _saveFile= MutableSharedFlow<ResponseState<ResponseFile>>()
    val saveFile= _saveFile.asSharedFlow()
    private val _message=MutableSharedFlow<ResponseState<MessageRequest>>()
    val  message=_message.asSharedFlow()
    private  val _loading= MutableSharedFlow<Boolean>()
    val loading=_loading.asSharedFlow()
    private  val _error= MutableSharedFlow<String>()
    val error=_error.asSharedFlow()
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun saveFile(multipartBody: MultipartBody.Part, name: RequestBody){
      multimediaRepository.saveFile(multipartBody,name).handleResult(_saveFile)

    }
    fun sendFileOrImage(id: String,body: FileOrImageMessage){
        chatRepository.sendFileOrImage(id, body).handleResult(_message)

    }

    fun sendMessage(id:String,body: MessageRequest){

            chatRepository.postMessage(id, body).handleResult(_message)
    }



}