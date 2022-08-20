package com.expostore.ui.fragment.chats.general

import kotlinx.coroutines.flow.MutableStateFlow

class PagerChatRepository private constructor(){
    private var author: MutableStateFlow<String> = MutableStateFlow("")
    private var id: MutableStateFlow<String> = MutableStateFlow("")
    fun getAuthorMessage(): MutableStateFlow<String> {
        return author
    }
    fun getIdChat(): MutableStateFlow<String> {
        return id
    }

    companion object {

        private val mInstance: PagerChatRepository =
           PagerChatRepository()

        @Synchronized
        fun getInstance(): PagerChatRepository {
            return mInstance
        }
    }
}