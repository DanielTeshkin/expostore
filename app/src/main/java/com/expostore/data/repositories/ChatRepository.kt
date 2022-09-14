package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.getchats.*
import com.expostore.data.local.db.LocalWorker
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