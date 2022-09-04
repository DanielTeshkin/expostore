package com.expostore.ui.fragment.product.personal.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.product.ProductInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class CreatePersonalProductViewModel @Inject constructor( private val addProductInteractor: ProductInteractor) : BaseViewModel() {

    private val _characteristics=
        MutableSharedFlow<ResponseState<List<CategoryCharacteristicModel>>>()
    val characteristics=_characteristics.asSharedFlow()
    private val _categories= MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories=_categories.asSharedFlow()
    private val _addProduct= MutableSharedFlow<ResponseState<CreateResponseProduct>>()
    val addProduct= _addProduct.asSharedFlow()


    init {
        loadCategories()
    }


    fun saveCategory(id: String){
        addProductInteractor.saveCategory(id)
        loadCategoryCharacteristic(id)
    }
    private fun loadCategories()=addProductInteractor.getCategories().handleResult(_categories)
    fun changeName(text:String)=addProductInteractor.changeName(text)
    fun changeLongDescription(text:String)=addProductInteractor.changeLongDescription(text)
    fun changeShortDescription(text:String)=addProductInteractor.changeShortDescription(text)
    fun changeCount(text:String)=addProductInteractor.changeCount(text)
    fun changePrice(text:String)=addProductInteractor.changePrice(text)
    private fun loadCategoryCharacteristic(id:String)=addProductInteractor.getCategoryCharacteristic(id).handleResult(_characteristics)
    private fun  addPhoto(id:String)= addProductInteractor.addPhoto(id)
    fun saveUri(image: Uri)=addProductInteractor.saveUri(image)
    fun removeImage(image:String)=addProductInteractor.removeImage(image)
    fun addFilterInput(left:String,right:String,name:String)=addProductInteractor.addFilterInput(left, right, name)
    fun addFilterSelect(name: String,list:List<String>)= addProductInteractor.addFilterSelect(name, list)
    fun addFilterRadio(id:String,name: String)=addProductInteractor.addFilterRadio(id,name)
    fun addFilterCheckbox(name: String,check:Boolean)=addProductInteractor.addFilterCheckbox(name, check)

    override fun onStart() {
        TODO("Not yet implemented")
    }

}