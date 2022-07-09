package com.expostore.api.pojo.getchats



import com.expostore.model.chats.DataMapping.User
import com.google.gson.annotations.SerializedName


data class ChatResponse(
    @SerializedName("id") val id : String = "",
    @SerializedName("items_chat" ) val itemsChatResponse : ArrayList<ItemChatResponse>? = arrayListOf(),
    @SerializedName("seller") val seller : UserResponse = UserResponse(),
    @SerializedName("buyer" ) val buyer : UserResponse = UserResponse(),
    @SerializedName("request_user") val request_user:UserResponse =UserResponse()
    )

