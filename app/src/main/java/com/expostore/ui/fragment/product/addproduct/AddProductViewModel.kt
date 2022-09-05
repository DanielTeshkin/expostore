package com.expostore.ui.fragment.product.addproduct

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.category.personal.create.CreatePersonalSelectionFragmentDirections
import com.expostore.ui.fragment.product.ProductInteractor
import com.expostore.ui.fragment.product.personal.create.CreatePersonalProductFragment
import com.expostore.ui.fragment.product.personal.create.CreatePersonalProductFragmentDirections
import com.expostore.ui.general.*
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductInteractor: ProductInteractor) : BaseViewModel() {
    private val _addProduct = MutableSharedFlow<ResponseState<CreateResponseProduct>>()
    val addProduct = _addProduct.asSharedFlow()
    private val _product = MutableStateFlow(ProductModel())
    val product = _product.asStateFlow()
    private val _updateProduct = MutableSharedFlow<ResponseState<CreateResponseProduct>>()
    private val _productPublic = MutableSharedFlow<ResponseState<ProductResponse>>()
    val productPublic = _productPublic.asSharedFlow()
    private val flag = MutableStateFlow(false)
    private val _connectionTypeState = MutableStateFlow(false)
    val connectionTypeState = _connectionTypeState.asStateFlow()
    private val shopId = MutableStateFlow("")
    private val save = MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    private val _categories = MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories = _categories.asSharedFlow()
    private val _characteristics =
        MutableSharedFlow<ResponseState<List<CategoryCharacteristicModel>>>()
    val characteristics = _characteristics.asSharedFlow()
    val category = MutableStateFlow<String?>(null)
    val enabled = addProductInteractor.enabled
    val characteristicState = addProductInteractor.characteristicState
    fun changeName(text: String) = addProductInteractor.changeName(text)
    fun changeLongDescription(text: String) = addProductInteractor.changeLongDescription(text)
    fun changeShortDescription(text: String) = addProductInteractor.changeShortDescription(text)
    fun changeCount(text: String) = addProductInteractor.changeCount(text)
    fun changePrice(text: String) = addProductInteractor.changePrice(text)
    fun updateConnectionTypeState(state: Boolean, flag: String){
        _connectionTypeState.value=state
        addProductInteractor.changeConnectionType(state,flag)
    }

    fun createOrUpdateProduct(
        context: Context,
        status: String,
        ) =
        when (addProductInteractor.checkUriSource()) {
            true -> saveImagesAndProduct(context,  status)
            false -> createOrUpdate(status)
        }


    private fun saveImagesAndProduct(context: Context, status: String) {
        loadToServerPhoto(context,status){
            createOrUpdate(it?:"my")
        }
    }

    private inline fun loadToServerPhoto(context: Context, status: String?=null,
                                         crossinline action:(String?)->Unit){
        saveImages(addProductInteractor.uriSource.value, context)
        viewModelScope.launch {
            save.collect { images ->
                if (images is ResponseState.Success) {
                    addPhoto(images.item.id[0])
                    action.invoke(status)
                }
            }
        }

    }

    fun saveCategory(id: String) {
        addProductInteractor.saveCategory(id)
        loadCategoryCharacteristic(id)
    }

    fun saveProductInformation(product: ProductModel) {
        _product.value = product
        shopId.value = product.shop.id
        product.images.map { addPhoto(it.id) }
        addProductInteractor.saveCharacteristic(product.characteristics)
        saveCategory(product.category?.id ?: "")
        flag.value = true
    }

    private fun published(id: String) {
        addProductInteractor.publishedProduct(id).handleResult(_productPublic)
    }


    private fun createOrUpdate(status: String) {
        val request = addProductInteractor.createRequest()
        when (flag.value) {
            true -> {
                if (status == "my") addProductInteractor.updateProduct(product.value.id, request)
                    .handleResult(_updateProduct, {
                        published(it.id ?: "")
                    })
                else addProductInteractor.updateProduct(product.value.id, request)
                    .handleResult(_updateProduct)
            }
            false -> createProduct(status)
        }
    }

    private fun createProduct(status: String) {
        when (status) {
            "my" -> addProductInteractor.createProduct().handleResult(_addProduct, {
                published(it.id ?: "")
            })
            else -> addProductInteractor.createProduct().handleResult(_addProduct)
        }
    }

    fun createPersonalProduct(context: Context) {
        loadToServerPhoto(context) {
            addProductInteractor.createPersonalProduct().handleResult(_addProduct, {
                    navigationTo(CreatePersonalProductFragmentDirections.actionCreatePersonalProductToFavoritesFragment())
            })
        }
    }


    private fun saveImages(list:List<Uri>,context:Context){

        list.map{ image ->
            Glide.with(context).asBitmap().load(image).into(object :
                CustomTarget<Bitmap>(){
                override fun onResourceReady( resource: Bitmap, transition: Transition<in Bitmap>?){
                    addProductInteractor.saveImage(resource).handleResult(save) }
                    override fun onLoadCleared(placeholder: Drawable?) {
                }
                })
        }

    }

    fun loadCategories()=addProductInteractor.getCategories().handleResult(_categories)
    private fun loadCategoryCharacteristic(id:String)=addProductInteractor.getCategoryCharacteristic(id).handleResult(_characteristics)
    private fun  addPhoto(id:String)= addProductInteractor.addPhoto(id)
    fun saveUri(image:Uri)=addProductInteractor.saveUri(image)
    fun removeImage(image:String)=addProductInteractor.removeImage(image)
    fun addFilterInput(left:String,right:String,name:String)=addProductInteractor.addFilterInput(left, right, name)
    fun addFilterSelect(name: String,list:List<String>)= addProductInteractor.addFilterSelect(name, list)
    fun addFilterRadio(id:String,name: String)=addProductInteractor.addFilterRadio(id,name)
    fun addFilterCheckbox(name: String,check:Boolean)=addProductInteractor.addFilterCheckbox(name, check)
    fun saveShopId(id:String)=addProductInteractor.saveShop(id)
    override fun onStart() {
    }
}