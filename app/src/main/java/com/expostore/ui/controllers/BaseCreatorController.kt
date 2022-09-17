package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.Character
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.fragment.product.addproduct.adapter.ProductCreateImageAdapter
import com.expostore.ui.fragment.product.addproduct.adapter.utils.ImagesEdit
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicsStateModel
import com.expostore.ui.general.toCharacteristicState
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseCreatorController(
    context: Context,
    private val addPhoto: () -> Unit,
    private val loadCharacteristics: (String) -> Unit

):BaseCharacteristicController(context),
    ImagesEdit {
    val list= mutableListOf (" ")
    protected  val mAdapter: ProductCreateImageAdapter by lazy {
        ProductCreateImageAdapter(list, this) }
    private val characteristicAdapter: CharacteristicInputRecyclerAdapter by lazy {
        CharacteristicInputRecyclerAdapter(context,this,"other",characteristicState.value)
    }
    abstract val fieldCategory:LinearLayout
    abstract val fieldCharacteristic:LinearLayout
    abstract val recycleViewCharacteristics:RecyclerView
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    protected val imageList=_imageList.asStateFlow()
    private val _enabled=MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    protected val name= MutableStateFlow("")
    protected val longDescription = MutableStateFlow("")
    val shortDescription= MutableStateFlow("")
    protected val count = MutableStateFlow("")
    protected val price = MutableStateFlow("")
    protected val category = MutableStateFlow("")
    val connectionType = MutableStateFlow("call_and_chatting")
    var status="my"
    fun addImages(list: List<String>) = imageList.value.addAll(list)

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
    fun uploadCharacteristics(list: List<CategoryCharacteristicModel>){
       fieldCharacteristic.apply {
            visibility = View.VISIBLE
            characteristicAdapter.removeAll()
            characteristicAdapter.addElement(list)
            recycleViewCharacteristics.layoutManager= LinearLayoutManager(context)
            recycleViewCharacteristics.adapter=characteristicAdapter
            selectListener {
                recycleViewCharacteristics.isVisible=it
            }
        }
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
    protected open fun checkUriSource():Boolean= multimedia.size>0
    fun createOrUpdate() =
        when (checkUriSource()) {
            true ->{
                Log.i("a","b")
                saveMultimedia()}
            false -> {
                Log.i("b","b")
                update()}
        }
    open fun saveMultimedia(){}
    open fun update(){}
    private fun saveUri(image:Uri){
        Log.i("uri",image.toString())
        multimedia.add(image)
        saveImageLocal()
        checkEnabled()
    }

    fun updateAdapter(uri: Uri){
        saveUri(uri)
        mAdapter.update(uri.toString())
    }
    private fun removeImage(image:String){
        if(_imageList.value.contains(image))_imageList.value.remove(image)
        else mapImages.remove(Uri.parse(image))
        checkEnabled()
    }
    fun installCategories(list: List<ProductCategoryModel>){
        fieldCategory.click {
            val categoryChose: CategoryChose = {
                category.value=it.id
                loadCharacteristics.invoke(it.id)
            }
            showBottomSpecifications(list, context, categoryChose)
        }
    }

    override fun openGallery() = addPhoto.invoke()
    override fun removePhoto(string: String) =removeImage(string)
}