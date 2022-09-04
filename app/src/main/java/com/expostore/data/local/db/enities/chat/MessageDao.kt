package com.expostore.data.local.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.data.remote.api.pojo.getchats.MessageResponse

@Entity
data class MessageDao(
    @PrimaryKey
    @ColumnInfo(name="id")val id: String="",
    @ColumnInfo(name="images")  val images: List<MessageImageDao>? = listOf(),
    @ColumnInfo(name="text") val text: String="",
    @ColumnInfo(name="dateCreated") val dateCreated: String="",
    @ColumnInfo(name="status") val status: String="",
    @ColumnInfo(name="author") val author: String="",
    @ColumnInfo(name="chatFiles") val chatFiles: List<FileDao> = arrayListOf())

val MessageResponse.toDao : MessageDao
get() = MessageDao(id,images?.map { it.toDaoMessageImage },text, dateCreated, status, author,chatFiles.map { it.toDao })