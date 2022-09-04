package com.expostore.ui.fragment.search.main.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.expostore.data.remote.api.base.BaseApiResponse
import com.expostore.data.remote.api.base.BaseListResponse
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel
import retrofit2.HttpException

typealias LoaderProducts = suspend (page:Int?) -> BaseApiResponse<BaseListResponse<ProductResponse>>
class ProductListPagingSource (private val loader:LoaderProducts) :
    PagingSource<Int, ProductModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {


      return  try {
            val pageNumber = params.key?:1

            val response= loader.invoke(pageNumber).result?.results?: emptyList()
           // val response = serverApi.getListProduct(page = pageNumber,null,null,null,null,null)


                val products = response.map { it.toModel }
                val nextPageNumber = if (products.isEmpty()) null else pageNumber+1
                val prevPageNumber = if (pageNumber > 1) pageNumber-1 else null
               LoadResult.Page(products?: emptyList(), prevPageNumber, nextPageNumber)


        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}