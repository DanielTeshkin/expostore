package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.model.product.Character
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.general.toCharacteristicState
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class BaseCreatorController(context: Context):BaseCharacteristicController(context) {
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    protected val imageList=_imageList.asStateFlow()
    val uriSource=MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val _enabled=MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    protected val name= MutableStateFlow("")
    protected val longDescription = MutableStateFlow("")
    val shortDescription= MutableStateFlow("")
    protected val count = MutableStateFlow("")
    protected val price = MutableStateFlow("")
    protected val category = MutableStateFlow("")
    private val connectionType = MutableStateFlow("call_and_chatting")
    private var status="my"

    fun changeShortDescription(text:String){
        shortDescription.value=text
        checkEnabled()
    }
    fun changeName(text:String){
        name.value=text
        checkEnabled()
    }
    fun changeLongDescription(text:String){
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
    fun updateStatus(state:String){
        status = state
    }
    open fun  checkEnabled(){

    }
    fun changeConnectionType(state: Boolean,flag:String){
        when(state and (flag=="call")){
            true-> connectionType.value="call_and_chatting"
            false->connectionType.value="chatting"
        }
        when(state and (flag=="message")){
            true-> connectionType.value="chatting"
            false->connectionType.value="call_and_chatting"
        }
    }
    fun updateEnabledState(state:Boolean){_enabled.value=state}
    protected open fun checkUriSource():Boolean= uriSource.value.isNotEmpty()
    fun createOrUpdate() =
        when (checkUriSource()) {
            true -> saveMultimedia()
            false -> update()
        }
    open fun saveMultimedia(){}
    open fun update(){}
    fun saveUri(image:Uri){
        uriSource.value.add(image)
        checkEnabled()
    }

    fun removeImage(image:String){
        if(_imageList.value.contains(image))_imageList.value.remove(image)
        else mapImages.remove(Uri.parse(image))
        checkEnabled()
    }
}