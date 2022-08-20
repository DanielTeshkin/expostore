package com.expostore.ui.fragment.tender.my.tabs

import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.tender.TenderInteractor
import com.expostore.ui.fragment.tender.my.MyTenderListFragmentDirections
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
@HiltViewModel
class MyTendersViewModel @Inject constructor(private val interactor: TenderInteractor)  : BaseViewModel() {
    private val _myTenders= MutableSharedFlow<ResponseState<List<TenderModel>>>()
    val myTender=_myTenders.asSharedFlow()

    fun loadMyTenders(status:String)=interactor.loadMyTenders(status).handleResult(_myTenders)
    fun navigateToEdit(model: TenderModel)=navigationTo(MyTenderListFragmentDirections.actionMyTendersToEditTenderFragment(model))
    override fun onStart() {
        TODO("Not yet implemented")
    }
}