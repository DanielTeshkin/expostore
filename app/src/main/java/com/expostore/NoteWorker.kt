package com.expostore

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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
) : CoroutineWorker(context,parameters) {
    override suspend fun doWork(): Result {
        val data=parameters.inputData.keyValueMap["input"] as NoteData
        val text=parameters.inputData.keyValueMap["text"] as String
        createOrUpdateNoteProduct(text,data)
        return  Result.success()
    }


   private suspend fun createOrUpdateNoteProduct(note:String, data: NoteData)  {
        if (data.flag=="product"&&data.isLiked==false) apiWorker.get().selectFavorite(data.id?:"", NoteRequest(text = note))
        else if (data.flag=="tender"&&data.isLiked==false) apiWorker.get().selectFavoriteTender(data.id?:"", NoteRequest(text = note))
        else if(data.flag=="product"&&data.isLiked==true) apiWorker.get().updateFavoriteProduct(data.id?:"", NoteRequest(text = note))
        else if(data.flag=="tender"&&data.isLiked==true) apiWorker.get().updateFavoriteTender(data.id?:"", NoteRequest(text = note))

    }
}