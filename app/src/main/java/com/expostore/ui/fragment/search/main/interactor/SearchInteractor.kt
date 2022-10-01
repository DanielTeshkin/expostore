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

) {




}
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }
