package com.expostore.ui.fragment.tender.list.pagging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.expostore.api.base.BaseApiResponse
import com.expostore.api.base.BaseListResponse
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.model.product.toModel
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.toModel
import retrofit2.HttpException
import java.io.IOException

typealias  LoadTenders = suspend (Int?) -> BaseListResponse<TenderModel>?

class TenderListPagingSource(private val loader: LoadTenders)  :
    PagingSource<Int, TenderModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TenderModel> {

        val page = params.key ?: 1
        return try {
            val response=loader.invoke(page.takeIf { it > 0 })

            //val response = apiWorker.getListProduct(page.takeIf { it > 0 })
            LoadResult.Page(
                response?.results ?: emptyList(),
                prevKey = if (page <= 0) null else page - 1,
                nextKey = if (response?.next.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TenderModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}