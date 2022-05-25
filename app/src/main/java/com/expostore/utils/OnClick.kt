package com.expostore.utils

import com.expostore.api.pojo.getchats.ResponseMainChat
import com.expostore.model.chats.DataMapping.MainChat

interface OnClick {
    fun onClickChat(position:Int, responseMainChat: MainChat)
}