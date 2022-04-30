package com.expostore.api.pojo.getchats



import com.google.gson.annotations.SerializedName


data class ResponseMainChat(
    @SerializedName("id") val id : String? = null,
    @SerializedName("items_chat" ) val itemsChatResponse : ArrayList<ItemChatResponse>? = arrayListOf(),
    @SerializedName("seller") val seller : UserResponse,
    @SerializedName("buyer" ) val buyer : UserResponse,
    @SerializedName("request_user") val request_user:UserResponse
    )

