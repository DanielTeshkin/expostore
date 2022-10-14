package com.expostore.ui.fragment.tender.create

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.expostore.data.remote.api.pojo.gettenderlist.TenderRequest
import com.expostore.data.remote.api.pojo.gettenderlist.TenderResponse
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.toRequestCreate
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.interactors.CreateTenderInteractor
import com.expostore.ui.base.vms.BaseCreatorViewModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.base.vms.ItemId

import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.fragment.tender.TenderInteractor
import com.expostore.ui.general.*

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TenderCreateViewModel @Inject constructor(override val interactor: CreateTenderInteractor) :
    BaseCreatorViewModel<TenderResponse,TenderResponse,TenderRequest>() {
    override val itemId: ItemId<TenderResponse>
        get() = {it.id?:""}
    private val location= MutableStateFlow("")
    init {
        getCategories()
    }

    override fun createRequest()= TenderRequest(
        category.value,
        imageList.value,
        name.value,
        longDescription.value,
        price.value,
        location.value,
       count= count.value.toIntOrNull(),
        communicationType = connectionType.value,
        characteristics = characteristicLoad()
    )
    fun changeLocation(text:String){
        location.value=text
    }
    fun getTender(id:String)=interactor.getTender(id).handleResult({
           updateEnabledState(it)
    },{
        navigationTo(TenderCreateFragmentDirections.actionTenderCreateFragmentToEditTenderFragment(it))
    },{
        it.message?.let { it1 -> Log.i("errorrr", it1) }
    })

    override fun checkEnabled() {
        updateEnabledState(
            name.value.isNotEmpty() and longDescription.value.isNotEmpty()and count.value.isNotEmpty()
                    and location.value.isNotEmpty()
                    and price.value.isNotEmpty() and(
                    (imageList.value.size!=0) or (getBitmapList().isNotEmpty()))
        )
    }

    override fun checkStackMultimedia(): Boolean = getBitmapList().isNotEmpty()

    override fun saveMultimedia() {
       checkPhoto()
    }




}