package com.expostore.ui.fragment.search.main.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.data.repositories.*
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.base.Loader
import com.expostore.ui.base.PagingSource
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.search.main.paging.LoaderProducts
import com.expostore.ui.fragment.search.main.paging.ProductListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class SearchInteractor @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val favoriteRepository: FavoriteRepository,
    private val profileRepository: ProfileRepository,
    private val chatRepository: ChatRepository,
    private val selectionRepository: SelectionRepository,
    private val comparisonRepository: ComparisonRepository
) {

    private val profileModel =MutableStateFlow<ProfileModel>(ProfileModel())
    fun selectFavorite(id: String) = favoriteRepository.addToFavorite(id)

  private suspend fun getProfileModel()= profileRepository.getProfile().collect { profileModel.value=it }

       suspend fun getProfile():ProfileModel? {
           getProfileModel()
           return profileModel.value
        }


     fun  createChat(id: String)= chatRepository.createChat(id, "product")

     fun addToSelection(id: String,product:String) =
         selectionRepository.addProductToSelection(id, ProductsSelection(listOf(product) ))

    fun getPersonalSelections()=selectionRepository.userSelectionList()

    fun getBaseListProducts()=productsRepository.getBaseListProducts()


    fun searchProducts(pagingConfig: PagingConfig = getDefaultPageConfig(),filterModel: FilterModel
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
            productsRepository.getProducts(page = it,FilterModel()) }
        val pagingSource=PagingSource( loaderProducts) { it.toModel }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory =
            { pagingSource }
        ).flow
    }


    fun addToComparison(id: String)=comparisonRepository.addToComparison(listOf(
        ComparisonProductData(id=id)
    ))
}
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }
