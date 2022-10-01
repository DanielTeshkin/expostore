package com.expostore.ui.fragment.chats.chat

import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.data.repositories.ChatRepository
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
    private val _info=MutableStateFlow<InfoItemChat?>(InfoItemChat())
    val info=_info.asStateFlow()
    fun saveInfo(infoItemChat: InfoItemChat?){
       _info.value=infoItemChat
    }


    override fun onStart() {
        TODO("Not yet implemented")
    }
}