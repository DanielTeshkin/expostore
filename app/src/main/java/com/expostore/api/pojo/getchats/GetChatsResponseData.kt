package com.expostore.api.pojo.getchats

import com.fasterxml.jackson.annotation.JsonProperty

data class GetChatsResponseData(
   val chats: ArrayList<Chat>?
)
