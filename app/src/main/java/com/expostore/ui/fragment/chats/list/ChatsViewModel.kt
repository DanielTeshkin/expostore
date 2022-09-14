package com.expostore.ui.fragment.chats.list


import androidx.lifecycle.viewModelScope
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.ChatRepository
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class ChatsViewModel @Inject constructor(private val chatRepository: ChatRepository) : BaseViewModel() {
    private val _chats= MutableSharedFlow<ResponseState<List<MainChat>>>()
    val chats=_chats.asSharedFlow()
    private val token= MutableStateFlow<String?>(value = null)

    fun chatsList() {
        chatRepository.chats()
            .handleResult(_chats)
    }
    fun openChatItem(infoItemChat: InfoItemChat){
        navigationTo(ChatsFragmentDirections.actionChatsFragmentToChatFragment(infoItemChat))
    }
    private fun navigateToOpen(){
        navigationTo(ChatsFragmentDirections.actionChatsFragmentToOpenFragment())
    }

    fun getToken(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                token.value=chatRepository.getToken()
            }
            check()
        }
    }

    fun check(){
        if(token.value.isNullOrEmpty())navigateToOpen()
    }

    override fun onStart() {

    }
}