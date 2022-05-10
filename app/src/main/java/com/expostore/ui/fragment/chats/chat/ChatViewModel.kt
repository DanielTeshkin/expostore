package com.expostore.ui.fragment.chats.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.general.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */

@HiltViewModel
class ChatViewModel @Inject constructor( private val repository: ChatRepository) : BaseViewModel() {
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
    fun getChatInfo()=repository.getChatInfo()

    override fun onStart() {
        TODO("Not yet implemented")
    }
}