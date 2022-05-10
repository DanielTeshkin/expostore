package com.expostore.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocalDataApi {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveChatInfo(chat: InfoChat)

    @Query( "SELECT * FROM info_chat")
    fun getChatInfo():InfoChat

}