package com.expostore

import android.util.Log
import androidx.work.*
import androidx.work.multiprocess.RemoteWorkManager
import com.expostore.data.remote.api.pojo.getprofile.EditProfileRequest
import com.expostore.data.repositories.ProfileRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PushService:FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("fcm",token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
              val data= workDataOf(Pair("chat_id",message.data["chat_id"]))
        val myWorkRequest = OneTimeWorkRequest.Builder(CurrentChatWorker::class.java)
            .setInputData(data)
            .setInitialDelay(1000, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(baseContext).enqueue(myWorkRequest)

    }
}