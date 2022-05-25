package com.expostore.ui.fragment.tender


import com.expostore.data.repositories.TenderRepository
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MyTenderListViewModel @Inject constructor( private val repository: TenderRepository) : BaseViewModel() {
    private val _myTenders=MutableSharedFlow<ResponseState<List<TenderModel>>>()
    val myTender=_myTenders.asSharedFlow()

    fun loadMyTenders()= repository.loadMyTenders().handleResult(_myTenders)

    fun navigate()= navigationTo(MyTenderListFragmentDirections.actionMyTendersToTenderCreateFragment())

    override fun onStart() {
       TODO()
    }


}