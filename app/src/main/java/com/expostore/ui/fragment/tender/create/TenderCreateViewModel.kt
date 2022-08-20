package com.expostore.ui.fragment.tender.create

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.expostore.api.pojo.gettenderlist.TenderRequest
import com.expostore.api.pojo.gettenderlist.TenderResponse
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.toRequestCreate
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel

import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.general.CheckBoxStateModel
import com.expostore.ui.general.InputStateModel
import com.expostore.ui.general.RadioStateModel
import com.expostore.ui.general.SelectStateModel
import com.expostore.ui.fragment.tender.TenderInteractor

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TenderCreateViewModel @Inject constructor(private val interactor: TenderInteractor) : BaseViewModel() {
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    private val  imageList=_imageList.asStateFlow()
    private val uriSource=MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val save= MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    private val category= MutableStateFlow<String?>(null)
    private val tenderResponse= MutableSharedFlow<ResponseState<TenderResponse>>()
    private val shop=MutableStateFlow<String>("")
    private val _categories= MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories=_categories.asSharedFlow()
    private val _characteristics=MutableSharedFlow<ResponseState<List<CategoryCharacteristicModel>>>()
    val characteristics=_characteristics.asSharedFlow()
    private val filterInputList= MutableStateFlow(InputStateModel(hashMapOf()))
    private val filterSelectList= MutableStateFlow(SelectStateModel(hashMapOf()))
    private val filterRadioList= MutableStateFlow(RadioStateModel(hashMapOf()))
    private val filterCheckBox= MutableStateFlow(CheckBoxStateModel(hashMapOf()))
    private val _filterCharacteristic= MutableStateFlow<List<CharacteristicFilterModel>>(mutableListOf())
    private val filterCharacteristic=_filterCharacteristic.asStateFlow()
    private val _tender=MutableStateFlow(TenderModel())
            val tender=_tender.asStateFlow()
    private val flag=MutableStateFlow(false)


    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun saveInfo(model: TenderModel){
        _tender.value=model
        _imageList.value.addAll(model.images?.map { it.id }?: listOf())
        flag.value=true
    }



    fun getMyShop(){
        viewModelScope.launch {
            interactor.getMyShop().collect {
              shop.value=it.id
            }
        }
    }

    fun createTender(name:String,location:String,count:String,price:String,description:String,
                     contact:String,
                     context: Context,
                       status:String?){
        characteristicLoad()
       if(uriSource.value.size>0) {
          saveImages(uriSource.value, context)
            viewModelScope.launch {
                save.collect { images ->
                    if (images is ResponseState.Success) {
                        addPhoto(images.item.id[0])
                        val requestData=TenderRequest(images = imageList.value,
                            title = name, location = location, count = count.toIntOrNull(), price = price,

                        description = description, category =category.value, communicationType = contact,  status = status,
                        characteristics =filterCharacteristic.value.map { it.toRequestCreate })
                       createOrUpdateTender(requestData)
                    }
                }
            }
        }
          else {
           val requestData = TenderRequest(
               images = imageList.value,
               title = name,
               location = location,
               count = count.toIntOrNull(),
               price = price,
               description = description,
               category = category.value,
               communicationType = contact,
              status = status,
               characteristics =  filterCharacteristic.value.map { it.toRequestCreate }

           )
           createOrUpdateTender(requestData)
       }
    }

    fun getCategories()=interactor.getCategories().handleResult(_categories)
    private fun getCategoriesCharacteristic(id: String) = interactor.getCategoryCharacteristic(id).handleResult(_characteristics)

    private fun saveImages(list:List<Uri>,context:Context){
        val bitmapList=ArrayList<Bitmap>()
        list.map{ uri ->
            Glide.with(context).asBitmap().load(uri).into(object :
                CustomTarget<Bitmap>(){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?){
                    bitmapList.add(resource)
                    val path= ImageMessage().encodeBitmapList(bitmapList)
                    val images = mutableListOf<SaveImageRequestData>()
                    path.map { images.add(SaveImageRequestData(it,"png")) }
                    interactor.saveImage(images).handleResult(save)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            }) }

    }

 private fun createOrUpdateTender(request: TenderRequest){
     when(flag.value){
         true-> {
             if(request.status=="my")interactor.updateTender(tender.value.id,request).handleResult(tenderResponse,{
                 published(it.id?:"")

             })
             else{
                 interactor.updateTender(tender.value.id,request).handleResult(tenderResponse)
             }}
         false->{
             createTender(request)

         }

     }
 }
    private fun createTender(request: TenderRequest) {
        when (request.status) {
            "my" -> interactor.createTender( request).handleResult(tenderResponse, {
                published(it.id?:"")

            })
            else -> interactor.createTender( request).handleResult(tenderResponse)
        }
    }
    private fun published(id:String)=interactor.publishedTender(id).handleResult(tenderResponse)


    fun  saveCategory(id: String){
        category.value=id
        getCategoriesCharacteristic(id)
    }

    fun addFilterInput(left:String,right:String,name:String){
        filterInputList.value.state[name] = Pair(left,right)
    }

    fun addFilterSelect(name: String,list:List<String>){
        filterSelectList.value.state[name] = list
    }
    fun addFilterRadio(id:String,name: String){

        filterRadioList.value.state[name]=id
    }
    fun addFilterCheckbox(name: String,check:Boolean){
        filterCheckBox.value.state[name]=check
    }
    fun characteristicLoad(){
        _filterCharacteristic.value=interactor.saveCharacteristicsState(filterInputList.value,filterRadioList.value,filterSelectList.value,filterCheckBox.value)

    }

    private fun  addPhoto(id:String){
        _imageList.value.add(id)
    }
    fun saveUri(image:Uri){
        uriSource.value.add(image)
    }
    fun deleteUri(position:Int){
        uriSource.value.removeAt(position)
    }
    fun navigateToTendersList(){
        navigationTo(TenderCreateFragmentDirections.actionTenderCreateFragmentToTenderListFragment())
    }

}