package com.expostore.ui.fragment.chats.dialog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.data.remote.api.pojo.getchats.FileOrImageMessage
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.getchats.ResponseFile
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveFileResponseData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.model.chats.DataMapping.ItemChat
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.fragment.chats.repeat
import com.expostore.ui.fragment.chats.toBase64
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class DialogViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val multimediaRepository: MultimediaRepository) : BaseViewModel() {
    private lateinit var update:Job
    private val _item=MutableSharedFlow<ResponseState<ItemChat>>()
    val item=_item.asSharedFlow()
    private val _message=MutableSharedFlow<ResponseState<MessageRequest>>()
    val  message=_message.asSharedFlow()
    private val _save=MutableStateFlow<ResponseState<SaveImageResponseData>>(ResponseState.Loading(false))
    val save=_save.asStateFlow()
    private val messageText= MutableStateFlow<String?>(null)
    private val _saveFile= MutableStateFlow<ResponseState<SaveFileResponseData>>(ResponseState.Loading(false))
    val saveFile= _saveFile.asStateFlow()
    

    override fun onStart() {
    }

    fun updateMessages(id:String){
        update=viewModelScope.repeat(5000){
                      chatRepository.chatItem(id).handleResult(_item)}
    }

    fun sentMessageOrUpdate(id: String, body: MessageRequest)= chatRepository.postMessage(id,body).handleResult(_message)

      fun saveMessageText(text:String){
          messageText.value=text
      }

    fun sendImages(id: String,list: List<String>)= sentMessageOrUpdate(id, MessageRequest(text =messageText.value, images = list as ArrayList<String>) )
    fun sendFiles(id: String,list: List<String>)=sentMessageOrUpdate(id, MessageRequest(text =messageText.value, chatFiles = list as ArrayList<String>) )
    fun saveFile(uris: List<SaveFileRequestData>)=multimediaRepository.saveFileBase64(uris).handleResult(_saveFile)

    fun saveImageNetwork(images: List<Bitmap>?= listOf()){
        val list = mutableListOf<SaveImageRequestData>()
        ImageMessage().encodeBitmapList(images as ArrayList<Bitmap>).map {
            list.add(SaveImageRequestData(it,"png"))
        }
        multimediaRepository.saveImage(list).handleResult(_save)

    }
    fun stopUpdate() {
        viewModelScope.launch {
            update.cancel()
        }
    }

    }




