package com.expostore.ui.fragment.search.main.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.expostore.api.ApiWorker
import com.expostore.ui.base.BaseInteractor
import com.expostore.ui.fragment.search.main.paging.ProductListPagingSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class SearchInteractor @Inject constructor(
    private val apiWorker: ApiWorker,
    private val pagingSource: ProductListPagingSource
) : BaseInteractor() {

    fun selectFavorite(id: String) = flow {
        handle { apiWorker.selectFavorite(id) }
        emit(Unit)
    }

    fun letProductFlow(pagingConfig: PagingConfig = getDefaultPageConfig()) = Pager(
        config = pagingConfig,
        pagingSourceFactory = { pagingSource }
    ).flow

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }
}