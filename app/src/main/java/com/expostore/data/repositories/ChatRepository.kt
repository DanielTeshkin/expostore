package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.getchats.*
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.local.db.enities.SaveFileRequestDao
import com.expostore.data.local.db.enities.SaveImageRequestDao
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.DataMapping.toModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
class ChatRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker) : BaseRepository() {

    fun createChat(id: String,flag:String)= flow {
        val result=handleOrDefault(ChatResponse()){apiWorker.createChat(id, flag)}
        emit(result.toModel )
    }

    fun chats() = operator(
        databaseQuery = { localWorker.getChats().map { it.toModel }},
        networkCall = { handleOrEmptyList { apiWorker.getChats()}.map { it.toModel }},
        clearCall = {localWorker.removeChats()},
        saveCallResult = { localWorker.saveChats(it)}
    )

   fun chatItem(id: String)=flow{
      val result= handleOrDefault(ItemChatResponse()){ apiWorker.getChat(id)}
        emit(result.toModel)
   }

    fun postMessage(id: String,body: MessageRequest)=flow{
        val result=handleOrDefault( MessageRequest()){apiWorker.messageCreate(body,id)}
        emit(result)
    }
    fun sendFileOrImage(id: String,body: FileOrImageMessage)=flow{
        val result=handleOrDefault( MessageRequest()){apiWorker.messageFileOrImage(body,id)}
        emit(result)
    }
    suspend fun getMainChat(id:String)= handleOrDefault(ChatResponse()){apiWorker.getMainChat(id)}.toModel
    suspend fun saveImages(images:List<SaveImageRequestDao>) = localWorker.saveImages(images)
    suspend fun saveFiles(files:List<SaveFileRequestDao>)=localWorker.saveFiles(files)


    fun deleteChat(id: String) = flow {
        val result=handleOrDefault(ItemChatResponse()){ apiWorker.deleteChat(id)}
        emit(result)
    }
    fun deleteMainChat(id: String) = flow {
        val result=handleOrDefault(ItemChatResponse()){ apiWorker.deleteChat(id)}
        emit(result)
    }

   fun getToken() = localWorker.getToken()




}