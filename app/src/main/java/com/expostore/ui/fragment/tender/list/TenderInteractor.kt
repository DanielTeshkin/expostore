package com.expostore.ui.fragment.tender.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.api.ApiWorker
import com.expostore.api.base.BaseListResponse
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.api.request.ChatCreateRequest
import com.expostore.api.request.TenderChat
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.TenderRepository
import com.expostore.db.LocalWorker
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.toModel
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.main.paging.ProductListPagingSource
import com.expostore.ui.fragment.tender.list.pagging.LoadTenders
import com.expostore.ui.fragment.tender.list.pagging.TenderListPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TenderInteractor @Inject constructor(private val tenderRepository: TenderRepository,
                                           private val chatRepository: ChatRepository,
                                                     private val favoriteRepository: FavoriteRepository) {
    fun letTenderFlow(pagingConfig: PagingConfig = getDefaultPageConfig(),
       filterModel: FilterModel=FilterModel()): Flow<PagingData<TenderModel>> {
        val loaderProducts: LoadTenders = { it ->
            tenderRepository.getTenders(page = it, filterModel = filterModel).result?.cast()
        }

        val pagingSource =TenderListPagingSource( loaderProducts)
        return Pager(
            config = pagingConfig,
            pagingSourceFactory =
            { pagingSource }
        ).flow

    }

    fun chatCreate(id:String)=chatRepository.createChat(id,"tender")
    fun selectFavorite(id:String)=favoriteRepository.updateSelectedTender(id)
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }
}
fun BaseListResponse<Tender>.cast():BaseListResponse<TenderModel>{
        return BaseListResponse(count, next, previous, results.map { it.toModel })
}