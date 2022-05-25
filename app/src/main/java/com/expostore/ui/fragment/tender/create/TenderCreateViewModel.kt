package com.expostore.ui.fragment.tender.create

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.MainActivity
import com.expostore.R

import com.expostore.api.ServerApi
import com.expostore.api.pojo.createtender.CreateTenderRequestData
import com.expostore.api.pojo.createtender.CreateTenderResponseData
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.AppPreferences
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.TenderRepository
import com.expostore.ui.base.BaseViewModel

import com.expostore.ui.fragment.chats.general.ImageMessage

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TenderCreateViewModel @Inject constructor(private val multimediaRepository: MultimediaRepository, private val tenderRepository: TenderRepository) : BaseViewModel() {


    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    private val  imageList=_imageList.asStateFlow()
    private val uriSource=MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val save= MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    private val category= MutableStateFlow<String?>(null)
    private val tenderResponse= MutableSharedFlow<ResponseState<CreateTenderResponseData>>()
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun createTender(name:String,location:String,count:String,priceFrom:String,priceUp:String,description:String,
                     contact:String,
                     context: Context){
      //  if(uriSource.value.size>0) {
            //saveImages(uriSource.value, context)
          //  viewModelScope.launch {
              //  save.collect { images ->
                   // if (images is ResponseState.Success) {
                      //  addPhoto(images.item.id[0])
                       // val requestData=CreateTenderRequestData(images = imageList.value,
                        //    title = name, location = location, count = count.toIntOrNull(), priceFrom = priceFrom, priceUpTo = priceUp,
                      //  description = description, category =category.value, communicationType = contact )
                     //  tenderRepository.createTender(requestData).handleResult(tenderResponse,{
                        //   navigationTo(TenderCreateFragmentDirections.actionTenderCreateFragmentToMyTenders())
                      // })
                 //   }
               // }
           // }
       // }
            val requestData=CreateTenderRequestData(images = imageList.value,
                title = name, location = location, count = count.toIntOrNull(), priceFrom = priceFrom, priceUpTo = priceUp,
                description = description, category =category.value, communicationType = contact )
            tenderRepository.createTender(requestData).handleResult(tenderResponse,{
                navigationTo(TenderCreateFragmentDirections.actionTenderCreateFragmentToMyTenders())
            })

    }
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

}