package com.expostore

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.repositories.ChatRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
@HiltWorker
class MessagingWorker @AssistedInject constructor(@Assisted private val context: Context, @Assisted
private val parameters: WorkerParameters, private val apiWorker: ApiWorker
) : CoroutineWorker(context,parameters) {
    override suspend fun doWork(): Result {
        val request= parameters.inputData.keyValueMap["images"] as List<*>
        val files= parameters.inputData.keyValueMap["files"] as List<*>
        val castRequest= mutableListOf<SaveImageRequestData>()
        val castFiles=mutableListOf<SaveFileRequestData>()
        request.map {
            if (it is SaveImageRequestData) castRequest.add(it)
            if (it is SaveFileRequestData) castFiles.add(it)
        }

        val images=apiWorker.saveImage(castRequest).result?.id
        val text=parameters.inputData.keyValueMap["text"] as String
        val id=parameters.inputData.keyValueMap["id"] as String
        apiWorker.messageCreate(MessageRequest( images =images as ArrayList<String>,text =text),id)
        return  Result.success()

    }
}