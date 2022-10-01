package com.expostore.ui.fragment.product.personal.create

import android.net.Uri
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.interactors.CreateProductInteractor
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.base.vms.CreatorProductViewModel
import com.expostore.ui.fragment.product.ProductInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class CreatePersonalProductViewModel @Inject constructor( override val interactor: CreateProductInteractor) : CreatorProductViewModel() {



    init {
        getCategories()
    }

    override fun navigateToMyProducts() {
        TODO("Not yet implemented")
    }

    override fun checkEnabled() {
        updateEnabledState(name.value.isNotEmpty() and longDescription.value.isNotEmpty()
                and shortDescription.value.isNotEmpty() and price.value.isNotEmpty() and(
                (imageList.value.size!=0) or (getBitmapList().isNotEmpty())))
    }

    override fun createOrUpdate() {
        super.createOrUpdate()
        interactor.createPersonalProduct(createRequest()).handleResult()
    }
    override fun checkStackMultimedia()=getBitmapList().isNotEmpty()

    override fun saveMultimedia() {
        loadImages(getBitmapList())
    }





    override fun onStart() {
        TODO("Not yet implemented")
    }

}