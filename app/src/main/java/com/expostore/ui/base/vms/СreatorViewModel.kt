package com.expostore.ui.base.vms

import com.expostore.ui.base.interactors.BaseCreatorInteractor
import com.expostore.ui.base.interactors.CreateInteractor
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class СreatorViewModel<T,A,E> :CharacteristicViewModel() {
   abstract override val interactor: BaseCreatorInteractor<T,A,E>
   private val _new= MutableSharedFlow<ResponseState<T>>()
    private val _public= MutableSharedFlow<ResponseState<A>>()
    fun create(request:E)=interactor.create(request).handleResult(_new)
    fun published(id:String)=interactor.published(id).handleResult(_public)
    fun update(id:String,request: E) = interactor.update(id,request).handleResult(_new)




}