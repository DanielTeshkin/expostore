package com.expostore.api.pojo.getchats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.ProductResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemChat(val id : String,
                    val messages : ArrayList<Message>,
                    val product : ProductResponse?,
                    val dateCreated : String? ): Parcelable

val ItemChatResponse.toModel : ItemChat
    get()= ItemChat(
    id=id,
    messageResponses.map { it.toModel }.cast(),
    product=product,
    dateCreated=dateCreated
    )

