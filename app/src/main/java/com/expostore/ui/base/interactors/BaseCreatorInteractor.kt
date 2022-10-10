package com.expostore.ui.base.interactors

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.data.remote.api.pojo.getcategory.Characteristic
import com.expostore.data.remote.api.pojo.getcategory.CharacteristicRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.ProductsRepository
import com.expostore.data.repositories.TenderRepository
import com.expostore.ui.fragment.chats.general.ImageMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

abstract class BaseCreatorInteractor<T,A,E> :CharacteristicsInteractor(),CreateInteractor<T,A,E>{
    abstract val multimedia:MultimediaRepository
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    protected val imageList=_imageList.asStateFlow()
    protected val mapImages= hashMapOf<Uri,Bitmap>()
    protected val name= MutableStateFlow("")
    protected val longDescription = MutableStateFlow("")
    val shortDescription= MutableStateFlow("")
    protected val count = MutableStateFlow("")
    protected val price = MutableStateFlow("")
    private val _enabled=MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    protected val categoryId = MutableStateFlow("")
    val connectionType = MutableStateFlow("call_and_chatting")
    var status="my"

    fun addImages(items:List<String>) = _imageList.value.addAll(items)
    fun saveImageLocal(uri: Uri,context: Context) {
         Glide.with(context).asBitmap().load(uri).centerCrop().into(object :
            CustomTarget<Bitmap>(){
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                mapImages[uri]=resource
            }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

        fun saveImage(resources:List<Bitmap>): Flow<SaveImageResponseData> = multimedia.saveImage(imageData(resources))

  // fun saveFiles()=multimedia.saveFileBase64()
    private fun imageData(resources: List<Bitmap>):MutableList<SaveImageRequestData>{
        val path= ImageMessage().encodeBitmapList(resources as ArrayList<Bitmap>)
        val images = mutableListOf<SaveImageRequestData>()
        path.map { images.add(SaveImageRequestData(it,"png")) }
        return images
    }


}