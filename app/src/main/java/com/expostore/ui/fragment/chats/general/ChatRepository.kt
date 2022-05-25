package com.expostore.ui.fragment.chats.general

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getchats.FileOrImageMessage
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.api.pojo.getchats.ResponseFile
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.InfoChat
import com.expostore.data.LocalRepository
import com.expostore.model.chats.DataMapping.toModel
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
class ChatRepository @Inject constructor(private val apiWorker: ApiWorker,private val repository: LocalRepository): BaseInteractor() {

    fun chats() =flow {
        val result=handleOrEmptyList { apiWorker.getChats()}.map { it.toModel }
        emit(result)
     }

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

    fun saveImage(saveImageRequestData: SaveImageRequestData)=
           flow{
               val result=handleOrDefault(SaveImageResponseData()){apiWorker.saveImage(saveImageRequestData)}
               emit(result)
           }
    fun saveFile(multipartBody: MultipartBody.Part,name:RequestBody) = flow {
        val result=handleOrDefault(ResponseFile()){apiWorker.fileCreate(multipartBody,name)}
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