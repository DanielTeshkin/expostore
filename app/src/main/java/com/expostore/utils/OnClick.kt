package com.expostore.utils

import com.expostore.model.chats.DataMapping.MainChat

interface OnClick {
    fun onClickChat(position:Int, responseMainChat: MainChat)
}