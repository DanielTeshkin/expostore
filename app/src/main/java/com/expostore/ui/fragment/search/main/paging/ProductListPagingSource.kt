package com.expostore.ui.fragment.search.main.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.expostore.api.ApiWorker
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductListPagingSource @Inject constructor(private val apiWorker: ApiWorker) :
    PagingSource<Int, ProductModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: 0
        return try {
            val response = apiWorker.getListProduct(page.takeIf { it > 0 })
            LoadResult.Page(
                response.result?.results?.map { it.toModel } ?: emptyList(),
                prevKey = if (page <= 0) null else page - 1,
                nextKey = if (response.result?.next.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        return null
    }
}