package com.expostore.data.remote.api.pojo.gettenderlist

import com.google.gson.annotations.SerializedName

data class TenderCategory(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String?,
        @SerializedName("sorting_number") val sortingNumber: Int?,
        @SerializedName("have_child") val have_child:Boolean?,
        @SerializedName("parent") val parent: ParentTender?
)
data class ParentTender(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String?,
        @SerializedName("sorting_number") val sortingNumber: Int?,
        @SerializedName("have_child") val have_child:Boolean?,
        @SerializedName("parent") val parent: ParentTender?=null
)