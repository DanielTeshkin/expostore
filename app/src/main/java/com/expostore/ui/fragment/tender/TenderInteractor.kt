package com.expostore.ui.fragment.tender

import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.data.remote.api.base.BaseListResponse
import com.expostore.data.remote.api.pojo.gettenderlist.Tender
import com.expostore.data.remote.api.pojo.gettenderlist.TenderRequest
import com.expostore.data.remote.api.pojo.gettenderlist.TenderResponse
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.repositories.*
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.toModel
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


    private val _enabled=MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    val characteristicState= MutableStateFlow(CharacteristicsStateModel())
    private val name= MutableStateFlow("")
    private val description = MutableStateFlow("")
    private val count = MutableStateFlow("")
    private val price = MutableStateFlow("")
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
    private val imageList=_imageList.asStateFlow()
    private val uriSource=MutableStateFlow<MutableList<Uri>>(mutableListOf())


    fun changeName(text:String){
        name.value=text
        checkEnabled()
    }
    fun changeLongDescription(text:String){
        description.value=text
        checkEnabled()
    }

    fun changeCount(text:String){
        count.value=text
        checkEnabled()
    }
    fun changePrice(text:String){
        price.value=text
        checkEnabled()
    }

    private fun checkEnabled(){
        if(name.value.isNotEmpty() and description.value.isNotEmpty()
            and description.value.isNotEmpty() and count.value.isNotEmpty() and price.value.isNotEmpty() and(
                    (_imageList.value.size!=0) or (uriSource.value.size!=0))
        ) updateEnabledState(true)
        else updateEnabledState(false)
    }
    private fun updateEnabledState(state:Boolean){_enabled.value=state}




    fun chatCreate(id:String)=chatRepository.createChat(id,"tender")

    fun selectFavorite(id:String)=favoriteRepository.addToFavoriteTender(id)

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }

    fun loadMyTenders(status:String)=tenderRepository.load(status)

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
    ): List<CharacteristicFilterModel?> =
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