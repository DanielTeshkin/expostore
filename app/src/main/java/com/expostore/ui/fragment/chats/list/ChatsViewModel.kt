package com.expostore.ui.fragment.chats.list

import com.expostore.data.InfoChat
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.general.ChatRepository
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class ChatsViewModel @Inject constructor(private val chatRepository: ChatRepository) : BaseViewModel() {
    private val _chats= MutableSharedFlow<ResponseState<List<MainChat>>>()
    val chats=_chats.asSharedFlow()

    fun chatsList() {
        chatRepository.chats()
            .handleResult(_chats)
    }
    fun openChatItem(){
        navigationTo(ChatsFragmentDirections.actionChatsFragmentToChatFragment())
    }

    override fun onStart() {
    }
}