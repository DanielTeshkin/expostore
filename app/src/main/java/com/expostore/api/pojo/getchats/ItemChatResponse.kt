package com.expostore.api.pojo.getchats


import com.google.gson.annotations.SerializedName

data class ItemChatResponse(
    @SerializedName("id" ) val id : String ="",
    @SerializedName("messages") val messageResponses : ArrayList<MessageResponse>? = arrayListOf(),
    @SerializedName("product") val product : ProductResponse= ProductResponse(),
    @SerializedName("date_created") val dateCreated : String? = null

)






