package com.expostore.data.remote.api.pojo.getchats



import com.google.gson.annotations.SerializedName


data class ChatResponse(
    @SerializedName("id") val id : String = "",
    @SerializedName("items_chat" ) val itemsChatResponse : ArrayList<ItemChatResponse>? = arrayListOf(),
    @SerializedName("seller") val seller : UserResponse = UserResponse(),
    @SerializedName("buyer" ) val buyer : UserResponse = UserResponse(),
    @SerializedName("request_user") val request_user:UserResponse =UserResponse()
    )

