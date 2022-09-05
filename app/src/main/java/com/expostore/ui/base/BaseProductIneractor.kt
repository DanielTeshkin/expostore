package com.expostore.ui.base

import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.remote.api.request.SelectionRequest
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.data.repositories.*
import com.expostore.model.product.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BaseProductInteractor @Inject constructor(private val chats: ChatRepository,
    private val favorite: FavoriteRepository,
    private val selection: SelectionRepository,
    private val comparison: ComparisonRepository,
     private val shop:ShopRepository
) {
    fun getSelections()=selection.userSelectionList()
    fun createChat(id:String,flag:String)=chats.createChat(id,flag)
    fun updateSelected(id:String)=favorite.addToFavorite(id)
    fun comparison(id: String)=comparison.addToComparison(listOf(ComparisonProductData(id)))
    fun addToSelection(id:String,product:String)=selection.addProductToSelection(id,
        ProductsSelection(listOf(product)))
    fun getProductFavoriteList()=favorite.getFavoritesList()
    fun getShop(id:String)=shop.getShop(id)
    fun deleteProductFromSelection(product: ProductModel,list:List<ProductModel>,id: String,name:String): Flow<SelectionResponse> {
        val products= mutableListOf<ProductModel>()
        products.addAll(list)
        products.remove(product)
        val list=products.map{it.id}
      return  selection.updateSelection(id = id,
            selectionRequest = SelectionRequest(name,list)
        )
    }
    fun deleteSelection(id:String)=selection.deleteUserSelection(id)
}