package com.expostore.ui.base.vms

import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseItemsInteractor
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow


abstract class BaseProductViewModel : BaseItemViewModel<ProductModel,SelectFavoriteResponseData,FavoriteProduct>() {
   abstract override val interactor
   :BaseItemsInteractor<ProductModel,SelectFavoriteResponseData,FavoriteProduct>
   private val _comparison= MutableSharedFlow<ResponseState<List<ComparisonProductData>>>()
    val comparison=_comparison.asSharedFlow()

    override fun addToComparison(id: String)=interactor.comparison(id).handleResult(_comparison)
    override fun addToSelection(id: String, product: String)=interactor.addToSelection(id,product).handleResult()
    abstract fun navigateToComparison()
    override fun onStart() {
    }

}