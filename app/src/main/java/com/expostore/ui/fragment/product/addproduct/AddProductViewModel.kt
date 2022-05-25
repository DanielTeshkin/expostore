package com.expostore.ui.fragment.product.addproduct

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.api.request.createProductRequest
import com.expostore.api.response.ProductResponseUpdate
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val multimediaRepository: MultimediaRepository,
                                              private val addProductInteractor: AddProductInteractor
                                               ) : BaseViewModel() {
    private val _addProduct= MutableSharedFlow<ResponseState<ProductResponseUpdate>>()
    val addProduct= _addProduct.asSharedFlow()
    private val _product=MutableStateFlow<ProductModel>(ProductModel())
     val product=_product.asStateFlow()
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    private val  imageList=_imageList.asStateFlow()
   private val uriSource=MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val flag=MutableStateFlow(false)
    private val connectionFlag=MutableStateFlow("call_and_chatting")
    private val shopId=MutableStateFlow<String>("")
    private val save=MutableSharedFlow<ResponseState<SaveImageResponseData>>()

    fun createOrUpdateProduct(context: Context,

        count:Int?,
                              name:String,
                              price: String?,
                              longDescription:String?,
                              shortDescription: String?,
                              status:String,
                               connectionType:String){
        if(uriSource.value.size>0) {
            saveImages(uriSource.value, context)
            viewModelScope.launch {
                save.collect { images ->
                    if (images is ResponseState.Success) {
                        addPhoto(images.item.id[0])
                        createOrUpdate(
                            count,
                            name,
                            price,
                            longDescription,
                            shortDescription,
                            status,
                            connectionType
                        )
                    }
                }
            }
        } else{ createOrUpdate(
            count,
            name,
            price,
            longDescription,
            shortDescription,
            status,
            connectionType
        )}


    }
    fun saveProductInformation(product:ProductModel){
        _product.value=product
        shopId.value=product.shop.id
        _imageList.value.addAll(product.images.map { it.id })
        flag.value=true
    }


  private  fun createOrUpdate(count:Int?,
                       name:String,
                       price: String?,
                       longDescription:String?,
                       shortDescription: String?,
                       status:String,
  connectionType: String){
        val request= createProductRequest(
            count,
            name,price,
            longDescription,
            shortDescription,
            imageList.value,
            connectionType
            )
               when(flag.value){
                   true-> {
                       if(status=="my")addProductInteractor.updateProduct(product.value.id,request).handleResult(_addProduct,{navigateToMyProducts()})
                      else{
                           addProductInteractor.putToDraft(product.value.id,request).handleResult(_addProduct,{navigateToMyProducts()})
                      }}
                   false->{
                       addProductInteractor.createProduct(shopId.value, request).handleResult(_addProduct)
                   }

               }




    }

    private fun saveImages(list:List<Uri>,context:Context){
        val bitmapList=ArrayList<Bitmap>()
        list.map{
            Glide.with(context).asBitmap().load(it).into(object :
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


    private fun  addPhoto(id:String){
        _imageList.value.add(id)
    }
    fun saveUri(image:Uri){
        uriSource.value.add(image)
    }

     fun navigateToMyProducts(){
        navigationTo(AddProductFragmentDirections.actionAddProductFragmentToMyProductsFragment())
    }
    private fun navigateToEditMy(){
        navigationTo(AddProductFragmentDirections.actionAddProductFragmentToEditMyProduct())
    }
    fun navigationToCategory(){
        navigationTo(AddProductFragmentDirections.actionAddProductFragmentToSpecificationsFragment())
    }




    override fun onStart() {
        TODO("Not yet implemented")
    }


}