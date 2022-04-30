package com.expostore.ui.fragment.chats

import androidx.lifecycle.viewModelScope
import com.expostore.api.pojo.getchats.ResponseMainChat
import com.expostore.ui.base.BaseViewModel
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
    private val _chats= MutableSharedFlow<ResponseState<List<ResponseMainChat>>>()
    val chats=_chats.asSharedFlow()

    fun chatsList() {
        viewModelScope.repeat(2000) {
        chatRepository.chats()
            .handleResult(_chats)
        }
   }
    fun openChatItem(name:String,username:String,id_list:Array<String>,id_image:Array<String>,product_names:Array<String>,author:String){
        navigationTo(ChatsFragmentDirections.actionChatsFragmentToChatFragment(name,username,id_list,id_image,product_names,author))
    }
    override fun onStart() {
    }
}