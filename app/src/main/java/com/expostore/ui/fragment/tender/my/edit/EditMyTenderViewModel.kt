package com.expostore.ui.fragment.tender.my.edit

import androidx.lifecycle.ViewModel
import com.expostore.api.pojo.gettenderlist.TenderResponse
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.tender.TenderInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditMyTenderViewModel @Inject constructor(val interactor:TenderInteractor) : BaseViewModel() {
    private val _tender=MutableStateFlow(TenderModel())
    val tender=_tender.asStateFlow()
    private val tenderResponse=MutableSharedFlow<ResponseState<TenderResponse>>()
    val textButton= MutableStateFlow("Cнять с публикации")
    val buttonVisible=MutableStateFlow(true)

    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun changeStatusPublished(){
        interactor.updateStatusTender(tender.value.id,tender.value.status?:"").handleResult(tenderResponse)
    }

    fun saveInfoTender(model:TenderModel){
        _tender.value=model
        if (model.status=="draft") textButton.value="Опубликовать"
        if (model.status=="blocked") buttonVisible.value=false
    }


    fun navigateToCreateTender()=navigationTo(EditMyTenderFragmentDirections.actionEditTenderFragmentToTenderCreateFragment())



}