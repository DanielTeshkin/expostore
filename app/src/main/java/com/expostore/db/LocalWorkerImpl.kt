package com.expostore.db
import android.content.Context
import com.expostore.db.enities.AdvertisingDao
import com.expostore.db.enities.ProfileDao
import com.expostore.db.enities.chat.ChatDao
import com.expostore.db.enities.chat.toDao
import com.expostore.db.enities.selection.SelectionDao
import com.expostore.db.enities.selection.toDao
import com.expostore.db.enities.toDao
import com.expostore.db.model.TokenModel
import com.expostore.db.model.toDao
import com.expostore.db.model.toModel
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat

class LocalWorkerImpl(private val localDataApi: LocalDataApi,context: Context):LocalWorker {
    override  fun getToken(): TokenModel? = localDataApi.getToken()?.toModel

    override suspend fun saveToken(tokenModel: TokenModel) =localDataApi.saveToken(tokenModel.toDao)

    override suspend fun removeToken() = localDataApi.removeToken()

    override suspend fun getChats(): List<ChatDao> =localDataApi.getChats()

    override suspend fun saveChats(chats: List<MainChat>) = localDataApi.saveChats(chats.map { it.toDao})

    override suspend fun removeChats() =localDataApi.removeChats()

    override suspend fun getProfile(): ProfileDao =localDataApi.getProfile()

    override suspend fun saveProfile(model: ProfileDao) = localDataApi.saveProfile(model)

    override fun deleteProfile() =localDataApi.deleteProfile()

    override suspend fun getSelection(): List<SelectionDao> =localDataApi.getSelection()

    override suspend fun saveSelections(selections: List<SelectionModel>) =localDataApi.saveSelections(selections.map {it.toDao  })

    override suspend fun removeSelections() =localDataApi.removeSelections()
    override suspend fun getAdvertising(): List<AdvertisingDao> =localDataApi.getAdvertising()

    override suspend fun saveAdvertising(advertising: List<CategoryAdvertisingModel>) =localDataApi.saveAdvertising(advertising.map { it.toDao })

    override suspend fun removeAdvertising() =localDataApi.removeAdvertising()

}


