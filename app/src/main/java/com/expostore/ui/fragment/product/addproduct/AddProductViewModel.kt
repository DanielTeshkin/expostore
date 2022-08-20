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
import com.expostore.api.request.ProductUpdateRequest
import com.expostore.api.request.createProductRequest
import com.expostore.api.response.CreateResponseProduct
import com.expostore.api.response.ProductResponse
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.toRequestCreate
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.fragment.product.ProductInteractor
import com.expostore.ui.general.*
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductInteractor: ProductInteractor) : BaseViewModel() {
    private val _addProduct= MutableSharedFlow<ResponseState<CreateResponseProduct>>()
    val addProduct= _addProduct.asSharedFlow()
    private val _product=MutableStateFlow(ProductModel())
    val product=_product.asStateFlow()
    private val _updateProduct= MutableSharedFlow<ResponseState<CreateResponseProduct>>()
    private val _productPublic= MutableSharedFlow<ResponseState<ProductResponse>>()
    val productPublic= _productPublic.asSharedFlow()
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    private val imageList=_imageList.asStateFlow()
   private val uriSource=MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val flag=MutableStateFlow(false)
    private val _connectionTypeState=MutableStateFlow(false)
    val connectionTypeState=_connectionTypeState.asStateFlow()
    private val shopId=MutableStateFlow("")
    private val save=MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    private val _categories= MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories=_categories.asSharedFlow()
    private val _characteristics=MutableSharedFlow<ResponseState<List<CategoryCharacteristicModel>>>()
    val characteristics=_characteristics.asSharedFlow()
    val category=MutableStateFlow<String?>(null)
    private val _enabled=MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    val characteristicState= MutableStateFlow(CharacteristicsStateModel())
    private val name= MutableStateFlow("")
    private val longDescription = MutableStateFlow("")
    private val shortDescription= MutableStateFlow("")
    private val count = MutableStateFlow("")
    private val price = MutableStateFlow("")



    fun changeName(text:String){
        name.value=text
        checkEnabled()
    }
    fun changeLongDescription(text:String){
        longDescription.value=text
        checkEnabled()
    }
    fun changeShortDescription(text:String){
        shortDescription.value=text
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

    private fun checkEnabled(){
     if(name.value.isNotEmpty() and longDescription.value.isNotEmpty()
         and shortDescription.value.isNotEmpty() and count.value.isNotEmpty() and price.value.isNotEmpty() and(
                 (_imageList.value.size!=0) or (uriSource.value.size!=0))
     ) updateEnabledState(true)
        else updateEnabledState(false)
    }

    private fun updateEnabledState(state:Boolean){_enabled.value=state}

    fun updateConnectionTypeState(state: Boolean){ _connectionTypeState.value=state }

    fun createOrUpdateProduct(context: Context, count:Int?, name:String, price: String?, longDescription:String?,
                              shortDescription: String?,
                              status:String,
                               connectionType:String){
        characteristicLoad()
        if(uriSource.value.size>0) {
            saveImages(uriSource.value, context)
            viewModelScope.launch {
                save.collect { images ->
                    if (images is ResponseState.Success) {
                        addPhoto(images.item.id[0])
                        createOrUpdate(count, name, price, longDescription, shortDescription, status, connectionType)
                    }
                }
            }
        } else { createOrUpdate(count, name, price, longDescription, shortDescription, status, connectionType)}

    }
    fun saveCategory(id: String){
        category.value=id
        loadCategoryCharacteristic(id)
    }
    fun saveProductInformation(product:ProductModel){
        _product.value=product
        shopId.value=product.shop.id
        _imageList.value.addAll(product.images.map { it.id })
        characteristicState.value=product.characteristics.toCharacteristicState()
        saveCategory(product.category?.id?:"")
        flag.value=true
    }
    private fun published(id: String){
        addProductInteractor.publishedProduct(id).handleResult(_productPublic)
    }


  private  fun createOrUpdate(count:Int?,
                       name:String,
                       price: String?,
                       longDescription:String?,
                       shortDescription: String?,
                       status:String,
  connectionType: String){
        val request= createProductRequest(count, name,price, longDescription, shortDescription, imageList.value, connectionType,
            characteristicLoad().map { it.toRequestCreate },
            category = category.value
            )
               when(flag.value){
                   true-> {
                       if(status=="my")addProductInteractor.updateProduct(product.value.id,request).handleResult(_updateProduct,{
                           published(it.id?:"")

                       })
                      else{
                           addProductInteractor.updateProduct(product.value.id,request).handleResult(_updateProduct)
                      }}
                   false->{
                       createProduct(shopId.value,request,status)

                   }

               }
          }

   private fun createProduct(id: String, request: ProductUpdateRequest, status: String) {
       when (status) {
           "my" -> addProductInteractor.createProduct(id, request).handleResult(_addProduct,{
               published(it.id?:"")
           })
           else -> addProductInteractor.createProduct(id, request).handleResult(_addProduct)
       }
   }

    private fun saveImages(list:List<Uri>,context:Context){
        val bitmapList=ArrayList<Bitmap>()
        list.map{ it ->
            Glide.with(context).asBitmap().load(it).into(object :
                CustomTarget<Bitmap>(){
                override fun onResourceReady( resource: Bitmap, transition: Transition<in Bitmap>?){
                    bitmapList.add(resource)
                    val path= ImageMessage().encodeBitmapList(bitmapList)
                    val images = mutableListOf<SaveImageRequestData>()
                    path.map { images.add(SaveImageRequestData(it,"png")) }
                    addProductInteractor.saveImage(images).handleResult(save) }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                }) }

    }

      fun loadCategories()=addProductInteractor.getCategories().handleResult(_categories)
      private fun loadCategoryCharacteristic(id:String)=addProductInteractor.getCategoryCharacteristic(id).handleResult(_characteristics)
      private fun characteristicLoad()= addProductInteractor.saveCharacteristicsState()
      private fun  addPhoto(id:String)= _imageList.value.add(id)
      fun saveUri(image:Uri){
          uriSource.value.add(image)
          checkEnabled()
      }

    fun removeImage(image:String){
       if(_imageList.value.contains(image))_imageList.value.remove(image)
        else uriSource.value.remove(Uri.parse(image))
        checkEnabled()
    }


    fun navigateToMyProducts()= navigationTo(AddProductFragmentDirections.actionAddProductFragmentToMyProductsFragment())
    fun addFilterInput(left:String,right:String,name:String)=addProductInteractor.addFilterInput(left, right, name)
    fun addFilterSelect(name: String,list:List<String>)=addProductInteractor.addFilterSelect(name, list)
    fun addFilterRadio(id:String,name: String)=addProductInteractor.addFilterRadio(id,name)
    fun addFilterCheckbox(name: String,check:Boolean)=addProductInteractor.addFilterCheckbox(name, check)

    fun saveShopId(id:String){shopId.value=id}

    override fun onStart() {
        loadCategories()
        viewModelScope.launch(Dispatchers.IO) {
          shopId.value= addProductInteractor.getProfile().shop?.id?:""
        }

    }
}