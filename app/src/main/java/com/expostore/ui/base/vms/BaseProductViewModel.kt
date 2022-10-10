package com.expostore.ui.base.vms

import android.util.Log
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseItemsInteractor
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow


abstract class BaseProductViewModel : BaseItemViewModel<ProductModel,SelectFavoriteResponseData,FavoriteProduct>() {
   abstract override val interactor:BaseProductInteractor
   private val _comparison= MutableSharedFlow<ResponseState<List<ComparisonProductData>>>()
    val comparison=_comparison.asSharedFlow()
    private val _selections= MutableSharedFlow<ResponseState<List<SelectionModel>>>()
    val selections= _selections.asSharedFlow()
    fun getSelections()= interactor.getSelections().handleResult(_selections)
    abstract fun navigateToCreateSelection(product: String)
    override fun navigateToItem(model: ProductModel) { Log.i("id",model.id)}
    fun addToComparison(id: String)=interactor.comparison(id).handleResult(_comparison)
    fun addToSelection(id: String, product: String)=interactor.addToSelection(id,product).handleResult()
    abstract fun navigateToComparison()

}