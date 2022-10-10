package com.expostore.ui.fragment.product.personal.create

import android.net.Uri
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel
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
    init { getCategories() }
    fun navigateToMyItem(model: ProductModel) {
        navigationTo(CreatePersonalProductFragmentDirections.actionCreatePersonalProductToPersonalProductfragment(model))
    }

    override fun checkEnabled() {
        updateEnabledState(name.value.isNotEmpty() and longDescription.value.isNotEmpty()
                and shortDescription.value.isNotEmpty() and price.value.isNotEmpty() and(
                (imageList.value.size!=0) or (getBitmapList().isNotEmpty())))
    }

    override fun createOrUpdate() {
        interactor.createPersonalProduct(createRequest()).handleResult({updateEnabledState(it)},{ product ->
            product.id?.let { id -> interactor.getPersonalProduct(id).handleResult({updateEnabledState(it)},{
                navigationTo(CreatePersonalProductFragmentDirections.actionCreatePersonalProductToPersonalProductfragment(it))
            }

            ) }

        })
    }
    override fun checkStackMultimedia()=getBitmapList().isNotEmpty()

    override fun saveMultimedia() {
        loadImages(getBitmapList())
    }







}