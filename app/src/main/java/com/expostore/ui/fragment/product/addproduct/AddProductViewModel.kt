package com.expostore.ui.fragment.product.addproduct

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.api.request.createProductRequest
import com.expostore.api.request.toRequestModel
import com.expostore.api.response.ProductResponseUpdate
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.fragment.search.filter.models.CheckBoxStateModel
import com.expostore.ui.fragment.search.filter.models.InputStateModel
import com.expostore.ui.fragment.search.filter.models.RadioStateModel
import com.expostore.ui.fragment.search.filter.models.SelectStateModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductInteractor: AddProductInteractor
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
    private val _categories= MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories=_categories.asSharedFlow()
    private val _characteristics=MutableSharedFlow<ResponseState<List<CategoryCharacteristicModel>>>()
    val characteristics=_characteristics.asSharedFlow()
    val category=MutableStateFlow<String>("")
    private val filterInputList= MutableStateFlow<InputStateModel>(InputStateModel(hashMapOf()))
    private val filterSelectList= MutableStateFlow<SelectStateModel>(SelectStateModel(hashMapOf()))
    private val filterRadioList= MutableStateFlow(RadioStateModel(hashMapOf()))
    private val filterCheckBox= MutableStateFlow(CheckBoxStateModel(hashMapOf()))
    private val _filterCharacteristic= MutableStateFlow<List<CharacteristicFilterModel>>(mutableListOf())
    val filterCharacteristic=_filterCharacteristic.asStateFlow()

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
    fun saveCategory(id: String){
        category.value=id
        loadCategoryCharacteristic(id)
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
        list.map{ it ->
            Glide.with(context).asBitmap().load(it).into(object :
                CustomTarget<Bitmap>(){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?){
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


    private fun  addPhoto(id:String){
        _imageList.value.add(id)
    }
    fun saveUri(image:Uri){
        uriSource.value.add(image)
    }

     fun navigateToMyProducts(){
        navigationTo(AddProductFragmentDirections.actionAddProductFragmentToMyProductsFragment())
    }
    fun addFilterInput(left:String,right:String,name:String){
        filterInputList.value.inputFilter[name] = Pair(left,right)
    }

    fun addFilterSelect(name: String,list:List<String>){
        filterSelectList.value.select[name] = list
    }
    fun addFilterRadio(id:String,name: String){
        filterRadioList.value.radioFilter[name]=id
    }
    fun addFilterCheckbox(name: String,check:Boolean){
        filterCheckBox.value.checkboxFilter[name]=check
    }
    fun searchFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            _filterCharacteristic.value =addProductInteractor.saveCharacteristicsState(
                filterInputList.value,
                filterRadioList.value, filterSelectList.value, filterCheckBox.value)
        }
    }


    override fun onStart() {
        TODO("Not yet implemented")
    }


}