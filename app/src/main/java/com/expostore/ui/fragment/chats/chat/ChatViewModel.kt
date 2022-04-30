package com.expostore.ui.fragment.chats.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author Teshkin Daniel
 */
class ChatViewModel : ViewModel() {
    private val _name= MutableStateFlow("")
     val name=_name.asStateFlow()
   private val _chatId= MutableStateFlow("")
    val chatId =_chatId.asStateFlow()
    fun loadName(name:String){
        _name.value= name
    }
    fun changeChat(id:String){
        _chatId.value=id
    }
}