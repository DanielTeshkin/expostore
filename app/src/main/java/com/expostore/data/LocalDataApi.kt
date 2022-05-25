package com.expostore.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.expostore.api.base.BaseApiResponse
import com.expostore.api.pojo.getchats.ResponseMainChat
import com.expostore.model.chats.DataMapping.MainChat

@Dao
interface LocalDataApi {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveChatInfo(chat: InfoChat)

    @Query( "SELECT * FROM info_chat")
    fun getChatInfo():InfoChat


    @Query( "SELECT * FROM chat_dao")
    fun getChats():List<ChatDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveChats(chats: List<ChatDao>?)





}