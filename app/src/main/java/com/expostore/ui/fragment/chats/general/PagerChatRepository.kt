package com.expostore.ui.fragment.chats.general

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import kotlinx.coroutines.flow.MutableStateFlow

class PagerChatRepository private constructor(){
    private var uris: MutableStateFlow<List<Uri>> = MutableStateFlow(listOf())
    private var images: MutableStateFlow<List<Uri>> = MutableStateFlow(listOf())
    private val openFileState = MutableStateFlow(false)
    private val bottomImage = MutableStateFlow(false)
    fun getUriFiles() =uris
    fun getOpenFileState()=openFileState
     fun getImagesOpen()=bottomImage
    fun getImages()=images


    companion object {
        private val mInstance: PagerChatRepository =
           PagerChatRepository()

        @Synchronized
        fun getInstance(): PagerChatRepository {
            return mInstance
        }
    }
}