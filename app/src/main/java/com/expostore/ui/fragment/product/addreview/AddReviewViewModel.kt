package com.expostore.ui.fragment.product.addreview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.expostore.data.remote.api.pojo.addreview.AddReviewResponseData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.ReviewsRepository
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(private val reviewsRepository: ReviewsRepository,private val multimediaRepository: MultimediaRepository) : BaseViewModel() {
    private val _imageList= MutableStateFlow<MutableList<String>>(mutableListOf())
    private val  imageList=_imageList.asStateFlow()
    private val uriSource= MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val save= MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    private  val id = MutableStateFlow<String>("")
    private val _uiAddReview= MutableSharedFlow<ResponseState<AddReviewResponseData>>()

    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun saveReview(text: String, rating: Int, context: Context){

            if(uriSource.value.size>0) {
                Log.i("id",id.value)
                saveImages(uriSource.value, context)

                viewModelScope.launch(Dispatchers.IO) {
                    save.collect { images ->
                        if (images is ResponseState.Success) {
                            addPhoto(images.item.id[0])

                           reviewsRepository.addReview(id.value,imageList.value,rating,text).handleResult(_uiAddReview)

                        }
                    }
                }
            }
            else {

                reviewsRepository.addReview(id.value, listOf(),rating,text).handleResult(_uiAddReview)
            }
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
    fun saveProduct(idProduct:String){
        id.value=idProduct
    }

    private fun  addPhoto(id:String){
        _imageList.value.add(id)
    }
    fun saveUri(image: Uri){
        uriSource.value.add(image)
    }


}