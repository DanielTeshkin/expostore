package com.expostore.data.repositories

import com.expostore.data.local.db.LocalWorker
import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.comparison.ComparisonResult
import com.expostore.model.product.toModel
import com.expostore.model.toDate
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComparisonRepository @Inject constructor(private val apiWorker: ApiWorker,
                                               private val localWorker: LocalWorker):BaseRepository() {

    fun makeComparisonRepository(products:List<ComparisonProductData>) = flow {
        val result =handleOrDefault(ComparisonResult()){apiWorker.comparison(products)}.toDate()
        emit(result)
    }

    fun getComparisonProducts() = flow {
        val result= handleOrEmptyList { apiWorker.getComparisonProducts() }.map { it.toModel }
        emit(result)
    }

    fun addToComparison(products:List<ComparisonProductData>) = flow {
        val result= handleOrEmptyList{apiWorker.addProductToComparison(products)}
        emit(result)
    }

    fun deleteFromComparison(products:List<ComparisonProductData>) = flow {
        val result= handleOrEmptyList{apiWorker.deleteFromComparisonProducts(products)}
        emit(result)
    }

}