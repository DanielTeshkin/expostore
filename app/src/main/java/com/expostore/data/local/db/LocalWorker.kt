package com.expostore.data.local.db
import com.expostore.data.local.db.enities.AdvertisingDao
import com.expostore.data.local.db.enities.ProfileDao
import com.expostore.data.local.db.enities.chat.ChatDao
import com.expostore.data.local.db.enities.favorites.FavoriteProductDao
import com.expostore.data.local.db.enities.selection.SelectionDao
import com.expostore.data.local.db.model.TokenModel
import com.expostore.data.remote.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat

interface LocalWorker {
   // fun getToken(): TokenModel?
  //  suspend fun saveToken(tokenModel: TokenModel)
//   suspend fun removeToken()
   suspend fun getChats():List<ChatDao>
    suspend fun saveChats(chats:List<MainChat>)
    suspend fun removeChats()
    suspend fun getProfile(): ProfileDao
    suspend fun saveProfile(model: ProfileDao)
    fun deleteProfile()
    suspend fun getSelection():List<SelectionDao>
    suspend fun getSelectionById(id:String):SelectionDao
    suspend fun getPersonalSelectionById(id: String):SelectionDao
    suspend fun saveSelections(selections: List<SelectionModel>)
    suspend fun removeSelections()
    suspend fun getAdvertising():List<AdvertisingDao>
    suspend fun saveAdvertising(advertising: List<CategoryAdvertisingModel>)
    suspend fun removeAdvertising()
    suspend fun getFavoritesProduct():List<FavoriteProductDao>
    suspend fun saveFavorites(list: List<FavoriteProductDao>)
    suspend fun removeFavorites()
    fun getToken():String?
    fun getRefreshToken():String?
    fun saveToken(tokenModel: TokenModel)
    fun removeToken()

}