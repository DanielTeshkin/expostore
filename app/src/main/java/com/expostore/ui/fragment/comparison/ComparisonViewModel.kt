package com.expostore.ui.fragment.comparison

import android.util.Log
import androidx.lifecycle.ViewModel
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.repositories.ComparisonRepository
import com.expostore.model.ComparisonModel
import com.expostore.model.chats.DataMapping.Product
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
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
    val currentPositionSecond= MutableStateFlow(2)
    val characteristicsResponse = MutableSharedFlow<ResponseState<List<ComparisonModel>>>()
    val comparisons = MutableStateFlow(listOf<ComparisonModel>())
    override fun onStart() {
        TODO("Not yet implemented")
    }



    fun startComparison() {
        comparisonRepository.getComparisonProducts().handleResult(
            productState,{
                products.value = it

                comparison(0,1)

            })
    }

    fun compar(){

        if (products.value.size > 1) {
                comparison(0,1)

            } else
                comparison(0,0)

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
            Log.i("compar",it.size.toString())
                comparisons.value=it
        })
    }

    fun deleteFromComparison(id:String)=comparisonRepository.deleteFromComparison(listOf(ComparisonProductData(id))).handleResult()
    fun navigateToProduct(model: ProductModel)=
        navigationTo(ComparisonFragmentDirections.actionComparisonToProductFragment(model))
}