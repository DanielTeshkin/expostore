package com.expostore.ui.base.vms

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.Character
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.ConditionProcessor
import com.expostore.ui.base.interactors.BaseCreatorInteractor
import com.expostore.ui.base.interactors.BaseItemsInteractor
import com.expostore.ui.base.interactors.CreateInteractor
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

typealias ItemId<T> =(T)->String

abstract class BaseCreatorViewModel<T,A,E> : CharacteristicViewModel() {
    abstract override val interactor:BaseCreatorInteractor<T,A,E>
    private val _item= MutableStateFlow<T?>(null)
    val item=_item.asStateFlow()
    private val _id= MutableStateFlow("")
    abstract val itemId: ItemId<T>
    private val _new= MutableSharedFlow<ResponseState<T>>()
    val new=_new.asSharedFlow()
    private val _public= MutableSharedFlow<ResponseState<A>>()
    val public=_public.asSharedFlow()
    private val _images= MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    val images=_images.asSharedFlow()
    private val _enabled=MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    val connectionType = MutableStateFlow("call_and_chatting")
    var status="my"
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    protected val imageList=_imageList.asStateFlow()
    protected val mapImages= hashMapOf<Uri,Bitmap>()
    protected val name= MutableStateFlow("")
    protected val longDescription = MutableStateFlow("")
    protected val count = MutableStateFlow("")
    protected val price = MutableStateFlow("")
    protected val processor= ConditionProcessor()
    fun loadImages(images:List<Bitmap>)= interactor.saveImage(images).handleResult(_images)
    fun create(request:E)=interactor.create(request).handleResult(_new)
    fun update(id:String,request: E) = interactor.update(id, request).handleResult(_new)
    private fun published(id:String)=interactor.published(id).handleResult(_public)
    protected fun updateEnabledState(state:Boolean){_enabled.value=state}
    fun getBitmapList()=mapImages.entries.map { it.value }
    fun checkPhoto(){ processor.checkCondition({ getBitmapList().isNotEmpty()},
        actionTrue = {loadImages(getBitmapList())},
            actionFalse = {createOrUpdate()}
        )
    }
   fun check() = when(checkStackMultimedia()){
           true -> saveMultimedia()
           false -> createOrUpdate()
       }
   fun handleLoadImages(list: List<String>){
       saveImages(list)
       createOrUpdate()
   }
  open fun createOrUpdate(){
        val request=createRequest()
        when (_id.value.isNotEmpty()) {
            true -> {
                if (status == "my")
                    interactor.update(_id.value, request).handleResult(_new,{
                    published(itemId.invoke(it))
                },{
                    _enabled.value=true
                })
                else interactor.update(_id.value, request)
                    .handleResult(_new,{

                    },{
                        _enabled.value=true
                    }) }
            false -> createProduct(request)
        }
    }
    fun saveConnectionType(type:String){ connectionType.value = type }
    fun saveItem(id:String){
        _id.value=id
    }
    fun updateStatus(state:String){ status=state }
    fun changeName(text:String){
        name.value=text
        checkEnabled()
    }
    fun changeDescription(text:String){
        longDescription.value=text
        checkEnabled()
    }

    fun changeCount(text:String){
        count.value=text
        checkEnabled()
    }
    fun changePrice(text:String){
        price.value=text
        checkEnabled()
    }
    fun disabledButton(){
        _enabled.value=false
    }
    private fun createProduct( request: E){
        when (status) {
            "my" -> interactor.create(request).handleResult(_new, {
                published(itemId.invoke(it))
            },{
                _enabled.value=true
            })
            else -> interactor.create(request).handleResult(_new,{},{
                _enabled.value=true
            }) } }

    fun saveImages(list: List<String>){ _imageList.value.addAll(list) }

    private fun saveImageLocal(uri: Uri, context: Context) {
        Glide.with(context).asBitmap().load(uri).centerCrop().into(object :
            CustomTarget<Bitmap>(){
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) { mapImages[uri]=resource }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }
    private fun saveImagesLocal(uri: MutableList<Uri>, context: Context) {
        uri.map {
            Glide.with(context).asBitmap().load(uri).centerCrop().into(object :
                CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    mapImages[it] = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        }
    }


   abstract fun createRequest():E
    fun removeImage(image:String){
        if(_imageList.value.contains(image))_imageList.value.remove(image)
        else mapImages.remove(Uri.parse(image))
        checkEnabled()
    }
    fun addPhoto(uri: Uri,context: Context)=saveImageLocal(uri,context)
    fun addImages(uri: MutableList<Uri>,context: Context)=saveImagesLocal(uri, context)
    abstract fun checkEnabled()
    abstract fun checkStackMultimedia():Boolean
    abstract fun saveMultimedia()

}