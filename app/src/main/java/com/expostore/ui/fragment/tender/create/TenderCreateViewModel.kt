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
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.ShopRepository
import com.expostore.data.repositories.TenderRepository
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.BaseViewModel

import com.expostore.ui.fragment.chats.general.ImageMessage

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TenderCreateViewModel @Inject constructor(private val multimediaRepository: MultimediaRepository,
                                                private val tenderRepository: TenderRepository,
                                                private val shopRepository: ShopRepository,
                                                private val categoryRepository: CategoryRepository) : BaseViewModel() {


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
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun getMyShop(){
        viewModelScope.launch {
            shopRepository.getMyShop().collect {
              shop.value=it.id
            }
        }
    }

    fun createTender(name:String,location:String,count:String,priceFrom:String,priceUp:String,description:String,
                     contact:String,
                     context: Context,
                       status:String?){
       if(uriSource.value.size>0) {
          saveImages(uriSource.value, context)
            viewModelScope.launch {
                save.collect { images ->
                    if (images is ResponseState.Success) {
                        addPhoto(images.item.id[0])
                        val requestData=TenderRequest(images = imageList.value,
                            title = name, location = location, count = count.toIntOrNull(), priceFrom = priceFrom, priceUpTo = priceUp,
                        description = description, category =category.value, communicationType = contact,  status = status )
                       tenderRepository.createTender(requestData).handleResult(tenderResponse,{
                           navigateToTendersList()
                       })
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
               priceFrom = priceFrom,
               priceUpTo = priceUp,
               description = description,
               category = category.value,
               communicationType = contact,
              status = status
           )
           tenderRepository.createTender(requestData).handleResult(tenderResponse,{
               navigateToTendersList()
           })
       }
    }

    fun getCategories()=categoryRepository.getCategories().handleResult(_categories)
    fun getCategoriesCharacteristic(id: String) = categoryRepository.getCategoryCharacteristic(id).handleResult(_characteristics)

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
                    multimediaRepository.saveImage(images).handleResult(save)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            }) }

    }
    fun  saveCategory(id: String){
        category.value=id
        getCategoriesCharacteristic(id)

    }
    fun navigateToCategory(){
        navigationTo(TenderCreateFragmentDirections.actionTenderCreateFragmentToSpecificationsFragment())
    }


    private fun  addPhoto(id:String){
        _imageList.value.add(id)
    }
    fun saveUri(image:Uri){
        uriSource.value.add(image)
    }
    fun navigateToTendersList(){
        navigationTo(TenderCreateFragmentDirections.actionTenderCreateFragmentToTenderListFragment())
    }

}