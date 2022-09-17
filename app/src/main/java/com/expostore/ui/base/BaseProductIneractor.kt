package com.expostore.ui.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.remote.api.request.SelectionRequest
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.data.repositories.*
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.fragment.search.filter.models.FilterModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

 class BaseProductInteractor @Inject constructor(private val chats: ChatRepository,
                                                     private val favorite: FavoriteRepository,
                                                     private val selection: SelectionRepository,
                                                     private val comparison: ComparisonRepository,
                                                     private val shop:ShopRepository,
                                                     private val profileRepository: ProfileRepository,
                                                     private val productsRepository: ProductsRepository
):BaseItemsInteractor<ProductModel,SelectFavoriteResponseData,FavoriteProduct> {
   override fun getSelections()=selection.userSelectionList()
  override  fun createChat(id:String,flag:String)=chats.createChat(id,"product")
     override fun  updateSelected(id: String) = favorite.addToFavorite(id)
    override fun comparison(id: String)=comparison.addToComparison(listOf(ComparisonProductData(id)))
    override fun addToSelection(id:String, product:String)=selection.addProductToSelection(id,
        ProductsSelection(listOf(product)))

     override fun  getFavoriteList()=favorite.getFavoritesList()

    fun getShop(id:String)=shop.getShop(id)
   override fun deleteProductFromSelection(product: ProductModel,list:List<ProductModel>,id: String,name:String): Flow<SelectionResponse> {
        val products= mutableListOf<ProductModel>()
        products.addAll(list)
        products.remove(product)
        val list=products.map{it.id}
      return  selection.updateSelection(id = id,
            selectionRequest = SelectionRequest(name,list)
        )
    }
   override fun deleteSelection(id:String)=selection.deleteUserSelection(id)
    private val profileModel = MutableStateFlow(ProfileModel())


    private suspend fun getProfileModel()= profileRepository.getProfile().collect { profileModel.value=it }

    suspend fun getProfile(): ProfileModel? {
        getProfileModel()
        return profileModel.value
    }

     override fun  getBaseList()= productsRepository.getBaseListProducts()
    fun getBaseListProducts()=productsRepository.getBaseListProducts()


    fun searchProducts(pagingConfig: PagingConfig, filterModel: FilterModel
    ): Flow<PagingData<ProductModel>> {

        val loaderProducts: Loader<ProductResponse> = { it ->
            productsRepository.getProducts(
                page = it,filterModel
            )
        }
        val pagingSource:PagingSource<ProductResponse,ProductModel> = PagingSource(loaderProducts)
        { it.toModel }
        return   Pager(
            config = pagingConfig,
            pagingSourceFactory =
            { pagingSource }
        ).flow
    }

    fun letProductFlow(pagingConfig: PagingConfig = getDefaultPageConfig(),
    ): Flow<PagingData<ProductModel>> {
        val loaderProducts: Loader<ProductResponse> = { it ->
            productsRepository.getProducts(page = it, FilterModel()) }
        val pagingSource=PagingSource( loaderProducts) { it.toModel }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory =
            { pagingSource }
        ).flow
    }
}
private fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 10, enablePlaceholders = false)
}