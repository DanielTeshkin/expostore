package com.expostore

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.expostore.data.local.db.LocalWorker
import com.expostore.data.local.db.enities.toRequest
import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.repositories.ChatRepository
import dagger.Lazy
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
@HiltWorker
class MessagingWorker @AssistedInject constructor(@Assisted private val context: Context, @Assisted
private val parameters: WorkerParameters, private val apiWorker: Lazy<ApiWorker>,private val localWorker: Lazy<LocalWorker>
) : CoroutineWorker(context,parameters) {
    override suspend fun doWork(): Result {
        val text=parameters.inputData.keyValueMap["text"] as String
        val id=parameters.inputData.keyValueMap["id"] as String
        Log.i("go",id)
        var request: MessageRequest=MessageRequest()
        if(parameters.inputData.keyValueMap["type"] as String == MessageType.FILE.name){
          val castFiles=localWorker.get().getFiles().map { it.toRequest }
          val saveFiles= apiWorker.get().saveFileBase64(castFiles).result?.files?: listOf()
          request=MessageRequest(text = text, chatFiles = saveFiles)
          localWorker.get().removeFiles()

      }
        else {
          val castRequest = localWorker.get().getImages().map { it.toRequest }
          val images = apiWorker.get().saveImage(castRequest).result?.id
          request=MessageRequest(images = images as ArrayList<String>, text = text)
            localWorker.get().removeImages()
        }
        val result =apiWorker.get().messageCreate(request, id)
        Log.i("result",result.result?.id?:"")
        return  Result.success()

    }
}

enum class MessageType {
    FILE,
    IMAGE
}