package com.expostore

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.expostore.data.local.db.LocalWorker
import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.request.NoteRequest
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.ui.fragment.note.NoteData
import dagger.Lazy
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn


@HiltWorker
class NoteWorker  @AssistedInject constructor(@Assisted private val context: Context, @Assisted
private val parameters: WorkerParameters, private val apiWorker:Lazy<ApiWorker>
) : CoroutineWorker(context, parameters) {
    override suspend fun doWork(): Result {
        val id=parameters.inputData.keyValueMap["id"] as String
        val flag=parameters.inputData.keyValueMap["flag"] as String
        val isLiked= parameters.inputData.keyValueMap["isLiked"] as Boolean
        val text=parameters.inputData.keyValueMap["text"] as String
        createOrUpdateNoteProduct(text,id,flag, isLiked)
        return  Result.success()
    }


   private suspend fun createOrUpdateNoteProduct(note:String, id:String,flag:String,isLiked:Boolean)  {
        if (flag=="product" && !isLiked) apiWorker.get().selectFavorite(id?:"", NoteRequest(text = note))
        else if (flag=="tender" && !isLiked) apiWorker.get().selectFavoriteTender(id?:"", NoteRequest(text = note))
        else if(flag=="product" && isLiked) apiWorker.get().updateFavoriteProduct(id?:"", NoteRequest(text = note))
        else if(flag=="tender" && isLiked) apiWorker.get().updateFavoriteTender(id?:"", NoteRequest(text = note))

    }
}