package com.expostore

import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.expostore.data.repositories.ChatRepository
import com.expostore.model.chats.DataMapping.toInfoModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@HiltWorker
class CurrentChatWorker @AssistedInject constructor(@Assisted private val context:Context, @Assisted
 private val parameters: WorkerParameters,private val chatRepository: ChatRepository) : CoroutineWorker(context,parameters) {

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            val id= parameters.inputData.keyValueMap["chat_id"] as String
            val chat = chatRepository.getMainChat(id)
            val info = chat.toInfoModel
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("info_chat", info)
            return@withContext
        }
        return Result.success()
    }



}

