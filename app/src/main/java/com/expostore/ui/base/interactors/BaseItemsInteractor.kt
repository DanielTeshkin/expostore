package com.expostore.ui.base.interactors

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.data.repositories.ProfileRepository
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.search.filter.models.FilterModel
import kotlinx.coroutines.flow.Flow

abstract class BaseItemsInteractor<T : Any,A,E> {
    abstract val user:ProfileRepository
  abstract  fun createChat(id:String):Flow<MainChat>
   abstract fun updateSelected(id:String):Flow<A>
  abstract  fun getFavoriteList() : Flow<List<E>>
  abstract  fun getBaseList():Flow<List<T>>
  abstract fun search(pagingConfig: PagingConfig= getDefaultPageConfig(), filterModel: FilterModel?=FilterModel()
    ): Flow<PagingData<T>>
   abstract fun letFlow(pagingConfig: PagingConfig= getDefaultPageConfig()): Flow<PagingData<T>>
   fun getToken()=user.getToken()
}

private fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 10, enablePlaceholders = false)
}
