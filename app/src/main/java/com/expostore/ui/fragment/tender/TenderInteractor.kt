package com.expostore.ui.fragment.tender

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.api.base.BaseListResponse
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.api.pojo.gettenderlist.TenderRequest
import com.expostore.api.pojo.gettenderlist.TenderResponse
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.repositories.*
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.toModel
import com.expostore.ui.fragment.search.filter.models.*
import com.expostore.ui.fragment.tender.list.pagging.LoadTenders
import com.expostore.ui.fragment.tender.list.pagging.TenderListPagingSource
import com.expostore.ui.general.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

typealias TenderOperation = () -> Flow<TenderResponse>

class TenderInteractor @Inject constructor(private val tenderRepository: TenderRepository,
                                           private val chatRepository: ChatRepository,
                                           private val favoriteRepository: FavoriteRepository,
                                           private val categoryRepository: CategoryRepository,
                                           private val multimediaRepository: MultimediaRepository,
                                           private val shopRepository: ShopRepository
                                           ) {



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

    fun loadMyTenders(status:String)=tenderRepository.loadMyTenders(status)

    fun getMyShop()=shopRepository.getMyShop()

    fun saveImage(saveImageRequestData: List<SaveImageRequestData>)=multimediaRepository.saveImage(saveImageRequestData)

    fun getCategories()=categoryRepository.getCategories()

    fun getCategoryCharacteristic(id: String) = categoryRepository.getCategoryCharacteristic(id)

    fun updateStatusTender(id: String,flag:String):Flow<TenderResponse> = when(flag) {
            "draft"->publishedTender(id)
             else -> tenderRepository.takeOff(id)
      }

    fun publishedTender(id:String) = tenderRepository.publishedTender(id)


    fun createTender(request: TenderRequest) = tenderRepository.createTender(request)

    fun updateTender(id: String,request: TenderRequest)=tenderRepository.updateTender(id, request)

    fun saveCharacteristicsState(inputStateModel: InputStateModel,
                                 radioStateModel: RadioStateModel,
                                 selectStateModel: SelectStateModel,
                                 checkBoxStateModel: CheckBoxStateModel
    ): List<CharacteristicFilterModel> =
        UiCharacteristicState().saveFilter(
            inputStateModel,
            radioStateModel,
            selectStateModel,
            checkBoxStateModel
        )




}
fun BaseListResponse<Tender>.cast():BaseListResponse<TenderModel>{
        return BaseListResponse(count, next, previous, results.map { it.toModel })
}