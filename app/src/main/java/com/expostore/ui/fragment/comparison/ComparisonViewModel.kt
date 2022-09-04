package com.expostore.ui.fragment.comparison

import androidx.lifecycle.ViewModel
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.repositories.ComparisonRepository
import com.expostore.model.ComparisonModel
import com.expostore.model.chats.DataMapping.Product
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ComparisonViewModel  @Inject constructor( private val comparisonRepository: ComparisonRepository): BaseViewModel() {
    private val productState=MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val products= MutableStateFlow(listOf<ProductModel>())
     val currentPositionFirst= MutableStateFlow(0)
    val currentPositionSecond= MutableStateFlow(1)
    private val characteristicsResponse = MutableSharedFlow<ResponseState<List<ComparisonModel>>>()
    val comparisons = MutableStateFlow(listOf<ComparisonModel>())
    override fun onStart() {
        TODO("Not yet implemented")
    }



    init {
        comparisonRepository.getComparisonProducts().handleResult(
            productState,
            {
                products.value = it
                if (it.size > 1) {
                    comparisonRepository.addToComparison(
                        products = listOf(
                            ComparisonProductData(it[0].id),
                            ComparisonProductData(it[1].id)
                        )
                    ).handleResult()

                } else
                    comparisonRepository.addToComparison(
                        products = listOf(
                            ComparisonProductData(it[0].id),
                            ComparisonProductData(it[0].id)
                        )
                    ).handleResult()
            })
    }
     fun changePosition1(position:Int){
         currentPositionFirst.value=position
     }
    fun changePosition2(position:Int){
        currentPositionSecond.value=position
    }


    fun comparison(positionFirstProduct:Int,positionSecondProduct: Int){
        comparisonRepository.makeComparisonRepository(products = listOf(ComparisonProductData(products.value[positionFirstProduct].id),
            ComparisonProductData(products.value[positionSecondProduct].id))).handleResult(characteristicsResponse,{
                comparisons.value=it
        })
    }

    fun deleteFromComparison(id:String)=comparisonRepository.deleteFromComparison(listOf(ComparisonProductData(id))).handleResult()
    fun navigateToProduct(model: ProductModel)=
        navigationTo(ComparisonFragmentDirections.actionComparisonToProductFragment(model))
}