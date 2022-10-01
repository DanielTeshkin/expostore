package com.expostore.ui.base.interactors

import kotlinx.coroutines.flow.Flow

interface CreateInteractor<T,A,E>  {
   fun published(id: String) : Flow<A>
   fun update(id:String,request:E) : Flow<T>
   fun create(request: E): Flow<T>

}