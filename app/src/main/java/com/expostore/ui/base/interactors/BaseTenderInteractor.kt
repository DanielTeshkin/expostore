package com.expostore.ui.base.interactors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.gettenderlist.Tender
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.data.repositories.*
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.toModel

import com.expostore.ui.base.interactors.BaseItemsInteractor
import com.expostore.ui.base.search.Loader
import com.expostore.ui.base.search.PagingSource
import com.expostore.ui.fragment.search.filter.models.FilterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BaseTenderInteractor @Inject constructor(private val chats: ChatRepository,
                                               private val favorite: FavoriteRepository,
                                               private val tenders: TenderRepository,
                                               override val user: ProfileRepository
): BaseItemsInteractor<TenderModel, SelectFavoriteTenderResponseData, FavoriteTender>() {
    override fun createChat(id: String)=chats.createChat(id,"tender")
    override fun updateSelected(id: String)=favorite.addToFavoriteTender(id)
    override fun getFavoriteList() = favorite.getFavoriteTenders()

    override fun getBaseList(): Flow<List<TenderModel>> {
        TODO("Not yet implemented")
    }
    fun getTender(id:String) =tenders.getTender(id)

    override fun search(
        pagingConfig: PagingConfig,
        filterModel: FilterModel?
    ): Flow<PagingData<TenderModel>> {
        val loaderProducts: Loader<Tender> = { it -> tenders.getTenders(page = it, filterModel = filterModel?:FilterModel())
        }

        val pagingSource: PagingSource<Tender, TenderModel> = PagingSource(loaderProducts) { it.toModel }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory =
            { pagingSource }
        ).flow
    }

    override fun letFlow(pagingConfig: PagingConfig): Flow<PagingData<TenderModel>> {
        TODO("Not yet implemented")
    }


}