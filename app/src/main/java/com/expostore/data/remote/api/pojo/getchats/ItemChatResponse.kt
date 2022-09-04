package com.expostore.data.remote.api.pojo.getchats


import com.expostore.data.remote.api.pojo.gettenderlist.Tender
import com.google.gson.annotations.SerializedName

data class ItemChatResponse(
    @SerializedName("id" ) val id : String ="",
    @SerializedName("messages") val messageResponses : ArrayList<MessageResponse>? = arrayListOf(),
    @SerializedName("product") val product : ProductResponse?=null,
    @SerializedName("tender") val tender: TenderChatResponse?=null,
    @SerializedName("date_created") val dateCreated : String? = null

)






