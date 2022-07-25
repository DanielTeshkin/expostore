package com.expostore.ui.fragment.search.main.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.expostore.api.request.ChatCreateRequest
import com.expostore.api.request.ProductChat
import com.expostore.api.request.ProductsSelection
import com.expostore.api.request.toRequestModel
import com.expostore.data.repositories.*
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.base.BaseInteractor
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
    private val selectionRepository: SelectionRepository
) : BaseInteractor() {

    private val profileModel =MutableStateFlow<ProfileModel>(ProfileModel())
    fun selectFavorite(id: String) = favoriteRepository.updateSelected(id)

  private suspend fun getProfileModel()= profileRepository.getProfile().collect { profileModel.value=it }

       suspend fun getProfile():ProfileModel? {
           getProfileModel()
           return profileModel.value
        }


     fun  createChat(id: String,flag:String)= chatRepository.createChat(id, flag)

     fun addToSelection(id: String,product:String) =
         selectionRepository.addProductToSelection(id, ProductsSelection(listOf(product) ))

    fun getPersonalSelections()=selectionRepository.userSelectionList()


    fun searchProducts(pagingConfig: PagingConfig = getDefaultPageConfig(),filterModel: FilterModel
   ): Flow<PagingData<ProductModel>> {

            val loaderProducts: LoaderProducts = { it ->
                productsRepository.getProducts(
                    page = it,filterModel
                    )
            }
           val pagingSource =  ProductListPagingSource( loaderProducts)
        return   Pager(
               config = pagingConfig,
               pagingSourceFactory =
               { pagingSource }
           ).flow

    }

    fun letProductFlow(pagingConfig: PagingConfig = getDefaultPageConfig(),
    ): Flow<PagingData<ProductModel>> {
        val loaderProducts: LoaderProducts = { it ->
            productsRepository.getProducts(page = it,FilterModel())
        }

        val pagingSource = ProductListPagingSource( loaderProducts)
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
