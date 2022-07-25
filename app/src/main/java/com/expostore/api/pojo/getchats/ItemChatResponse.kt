package com.expostore.api.pojo.getchats


import com.expostore.api.pojo.gettenderlist.Tender
import com.google.gson.annotations.SerializedName

data class ItemChatResponse(
    @SerializedName("id" ) val id : String ="",
    @SerializedName("messages") val messageResponses : ArrayList<MessageResponse>? = arrayListOf(),
    @SerializedName("product") val product : ProductResponse?=null,
    @SerializedName("tender") val tender: Tender?=null,
    @SerializedName("date_created") val dateCreated : String? = null

)






