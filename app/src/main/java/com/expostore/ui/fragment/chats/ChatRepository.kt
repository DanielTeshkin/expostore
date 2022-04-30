package com.expostore.ui.fragment.chats

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
class ChatRepository @Inject constructor(private val apiWorker: ApiWorker): BaseInteractor() {

    fun chats() =flow {
        val result=handleOrEmptyList { apiWorker.getChats()}
        emit(result)
     }

   fun chatItem(id: String)=flow{
      val result= handleOrDefault(ItemChatResponse()){ apiWorker.getChat(id)}
        emit(result)
   }

    fun postMessage(id: String,body: MessageRequest)=flow{
        val result=handleOrDefault( MessageRequest()){apiWorker.messageCreate(body,id)}
        emit(result)
    }
       fun saveImage(saveImageRequestData: SaveImageRequestData)=
           flow{
               val result=handleOrDefault(SaveImageResponseData()){apiWorker.saveImage(saveImageRequestData)}
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


}