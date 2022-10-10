package com.expostore.ui.base.interactors

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.search.filter.models.FilterModel
import kotlinx.coroutines.flow.Flow

interface BaseItemsInteractor<T : Any,A,E> {
    fun createChat(id:String):Flow<MainChat>
    fun updateSelected(id:String):Flow<A>
    fun getFavoriteList() : Flow<List<E>>
    fun getBaseList():Flow<List<T>>
    fun search(pagingConfig: PagingConfig= getDefaultPageConfig(), filterModel: FilterModel?=FilterModel()
    ): Flow<PagingData<T>>
    fun letFlow(pagingConfig: PagingConfig= getDefaultPageConfig()): Flow<PagingData<T>>
}

private fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 10, enablePlaceholders = false)
}
