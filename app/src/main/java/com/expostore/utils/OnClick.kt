package com.expostore.utils

import com.expostore.api.pojo.getchats.ResponseMainChat

interface OnClick {
    fun onClickChat(position:Int, responseMainChat: ResponseMainChat)
}