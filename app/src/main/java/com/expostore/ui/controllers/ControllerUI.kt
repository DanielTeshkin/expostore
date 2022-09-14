package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.base.ConditionProcessor
import com.expostore.ui.fragment.chats.dialog.adapter.DialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.dialog.adapter.getFileName
import com.expostore.ui.fragment.chats.fragment.ImageDownload
import com.expostore.ui.fragment.chats.down
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.utils.OnClickImage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

typealias ProcessingImagesData = (Uri?,Bitmap?) ->Unit
open class ControllerUI(val context: Context) {
    protected val mapImages= hashMapOf<Uri,Bitmap>()
    protected val multimedia:MutableList<Uri> = mutableListOf()
    private val fileStorage= FileStorage(context)
    protected val processor= ConditionProcessor()
    fun openImageFragment(bitmap: Bitmap): ImageDownload {
        return ImageDownload(bitmap)
    }

    protected  fun saveImageLocal() {
        multimedia.map{
            Glide.with(context).asBitmap().load(it).centerCrop().into(object :
                CustomTarget<Bitmap>(){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    mapImages[it]=resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            }) } }

  protected fun saveFile(): MutableList<SaveFileRequestData> {
     val list= mutableListOf<SaveFileRequestData>()
     multimedia.map {
       list.add(fileStorage.getSaveRequestData(it))
     }
    return list
   }
  fun clearMultimedia()=multimedia.clear()

    protected fun processingImagesData(uri: Uri,resource:Bitmap){
        mapImages[uri]=resource
    }
    fun toRequestDataList(){
        val list = mutableListOf<SaveImageRequestData>()
        ImageMessage().encodeBitmapList(mapImages.entries.map { it.value } as ArrayList<Bitmap>).map {
            list.add(SaveImageRequestData(it,"png"))
        }
    }
    fun getBitmapList()=mapImages.entries.map { it.value }



}
