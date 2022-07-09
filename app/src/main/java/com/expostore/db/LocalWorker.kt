package com.expostore.db
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.expostore.api.pojo.getchats.ChatResponse
import com.expostore.db.enities.AdvertisingDao
import com.expostore.db.enities.ProfileDao
import com.expostore.db.enities.chat.ChatDao
import com.expostore.db.enities.selection.SelectionDao
import com.expostore.db.model.TokenModel
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.profile.ProfileModel

interface LocalWorker {

     fun getToken(): TokenModel?
    suspend fun saveToken(tokenModel: TokenModel)
   suspend fun removeToken()
   suspend fun getChats():List<ChatDao>
    suspend fun saveChats(chats:List<MainChat>)
    suspend fun removeChats()
    suspend fun getProfile(): ProfileDao
    suspend fun saveProfile(model: ProfileDao)
    fun deleteProfile()
    suspend fun getSelection():List<SelectionDao>
    suspend fun saveSelections(selections: List<SelectionModel>)
    suspend fun removeSelections()
    suspend fun getAdvertising():List<AdvertisingDao>
    suspend fun saveAdvertising(advertising: List<CategoryAdvertisingModel>)
    suspend fun removeAdvertising()
}