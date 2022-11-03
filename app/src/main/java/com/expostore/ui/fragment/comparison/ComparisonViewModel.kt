package com.expostore.ui.fragment.comparison

import android.util.Log
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.repositories.ComparisonRepository
import com.expostore.model.ComparisonModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
@HiltViewModel
class ComparisonViewModel  @Inject constructor( private val comparisonRepository: ComparisonRepository): BaseViewModel() {
    val productState=MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val products= MutableStateFlow(listOf<ProductModel>())
     val currentPositionFirst= MutableStateFlow(1)
    val currentPositionSecond= MutableStateFlow(1)
    val characteristicsResponse = MutableSharedFlow<ResponseState<List<ComparisonModel>>>()
    val delete = MutableSharedFlow<ResponseState<List<ComparisonProductData>>>()
    val comparisons = MutableStateFlow(listOf<ComparisonModel>())
    override fun onStart() {
        TODO("Not yet implemented")
    }



    fun startComparison() {
        comparisonRepository.getComparisonProducts().handleResult(
            productState,{
                products.value = it
                currentPositionSecond.value=+1
                comparison(0,1)

            })
    }

    fun compar(){

        if (products.value.size > 1) {
                comparison(currentPositionFirst.value,currentPositionSecond.value)

            } else
                comparison(0,0)

        }
     fun changePosition1(position:Int){
         currentPositionFirst.value=position+1


     }
    fun changePosition2(position:Int){
        currentPositionSecond.value=position+1

    }


    fun comparison(positionFirstProduct:Int,positionSecondProduct: Int){
        if (products.value.size > 1) {
            comparisonRepository.makeComparisonRepository(
                products = listOf(
                    ComparisonProductData(products.value[positionFirstProduct].id),
                    ComparisonProductData(products.value[positionSecondProduct].id)
                )
            ).handleResult(characteristicsResponse, {
                Log.i("compar", it.size.toString())
                comparisons.value = it
            })
        }
    }

    fun deleteFromComparison(id:String)= comparisonRepository.deleteFromComparison(id).handleResult(delete)

    fun navigateToProduct(model: ProductModel)=
        navigationTo(ComparisonFragmentDirections.actionComparisonToProductFragment(model))
}