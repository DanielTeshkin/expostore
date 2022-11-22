package com.expostore.ui.fragment.chats.dialog

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.expostore.MessageType
import com.expostore.MessagingWorker
import com.expostore.data.local.db.enities.SaveFileRequestDao
import com.expostore.data.local.db.enities.SaveImageRequestDao
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveFileResponseData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.model.chats.DataMapping.ItemChat
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.fragment.chats.repeat
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class DialogViewModel @Inject constructor(private val chatRepository: ChatRepository,private val workManager: WorkManager,
    private val multimediaRepository: MultimediaRepository) : BaseViewModel() {
    private lateinit var update: Job
    private val _item = MutableSharedFlow<ResponseState<ItemChat>>()
    val item = _item.asSharedFlow()
    private val _message = MutableSharedFlow<ResponseState<MessageRequest>>()
    val message = _message.asSharedFlow()
    private val _save =
        MutableStateFlow<ResponseState<SaveImageResponseData>>(ResponseState.Loading(false))
    val save = _save.asStateFlow()
    private val messageText = MutableStateFlow<String?>(null)
    private val _saveFile =
        MutableStateFlow<ResponseState<SaveFileResponseData>>(ResponseState.Loading(false))
    val saveFile = _saveFile.asStateFlow()
    override fun onStart() {
    }

    fun updateMessages(id: String) {
        update = viewModelScope.repeat(60000) {
            chatRepository.chatItem(id).handleResult(_item)
        }
    }

    fun sentMessageOrUpdate(id: String, body: MessageRequest) =
        chatRepository.postMessage(id, body).handleResult(_message)

    fun saveMessageText(text: String) {
        messageText.value = text
    }

    fun sendImages(id: String, list: List<String>) = sentMessageOrUpdate(
        id,
        MessageRequest(text = messageText.value, images = list as ArrayList<String>)
    )

    fun sendFiles(id: String, list: List<String>) = sentMessageOrUpdate(
        id,
        MessageRequest(text = messageText.value, chatFiles = list as ArrayList<String>)
    )

    fun sendMessageWithFile(
        uris: List<SaveFileRequestDao>,
        context: Context,
        id: String,
        text: String
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { chatRepository.saveFiles(uris) }
            val result = Data.Builder()
                .putString("id", id)
                .putString("text", text)
                .putString("type", MessageType.FILE.name).build()
            val myWorkRequest = OneTimeWorkRequest.Builder(MessagingWorker::class.java).addTag("files")
                .setInputData(result).build()
          workManager.enqueue(myWorkRequest)

        }
    }

    fun sendMessageWithImage(
        images: List<Bitmap>? = listOf(),
        context: Context,
        text: String,
        id: String
    ) = viewModelScope.launch {
        val list = mutableListOf<SaveImageRequestDao>()
        ImageMessage().encodeBitmapList(images as ArrayList<Bitmap>)
            .map { list.add(SaveImageRequestDao(it, "png")) }

      val jod= async (Dispatchers.IO) {
          chatRepository.saveImages(list)
          Log.i("data", list.size.toString())
      }
        jod.await()

        Log.i("go","ffff")
        val result = Data.Builder()
            .putString("id", id)
            .putString("text", text)
            .putString("type", MessageType.IMAGE.name).build()
        val myWorkRequest = OneTimeWorkRequest.Builder(MessagingWorker::class.java).addTag("images").setInputData(result).build()
        Log.i("go","ffff")
        workManager.enqueue(myWorkRequest)

    }


    fun stopUpdate() {
        viewModelScope.launch {
            update.cancel()
        }
    }
}





