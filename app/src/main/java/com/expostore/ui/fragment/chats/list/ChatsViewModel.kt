package com.expostore.ui.fragment.chats.list


import androidx.lifecycle.viewModelScope
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.ProfileRepository
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
     private val openPageIntent= MutableStateFlow(false)




    fun getChatsListIntent()= when(!chatRepository.getToken().isNullOrEmpty()){
       true-> chatRepository.chats().handleResult(_chats)
        false->navigateToOpen()
    }

    fun openChatItem(infoItemChat: InfoItemChat){
        navigationTo(ChatsFragmentDirections.actionChatsFragmentToChatFragment(infoItemChat))
    }
    private fun navigateToOpen(){
        navigationTo(ChatsFragmentDirections.actionChatsFragmentToOpenFragment())
    }



    override fun onStart() {

    }
}