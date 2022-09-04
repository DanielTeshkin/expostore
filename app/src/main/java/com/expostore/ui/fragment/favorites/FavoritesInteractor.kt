package com.expostore.ui.fragment.favorites

import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.repositories.*
import javax.inject.Inject

class FavoritesInteractor @Inject constructor(private val favoriteRepository: FavoriteRepository,
                                              private val chatRepository: ChatRepository,
                                              private val searchRepository: SearchRepository,
                                               private val selectionRepository: SelectionRepository,
                                               private val productsRepository: ProductsRepository,
                                              private val comparisonRepository: ComparisonRepository

                                               ) {

    fun getProductFavoriteList()=favoriteRepository.getFavoritesList()
    fun getTenderFavoriteList()=favoriteRepository.getFavoriteTenderList()
    fun updateSelected(id:String)=favoriteRepository.addToFavorite(id)
    fun updateSelectedTender(id:String)=favoriteRepository.addToFavoriteTender(id)
    fun getSearchList()=searchRepository.saveSearchList()
    fun deleteSaveSearch(id:String)=searchRepository.deleteSaveSearch(id)
    fun chatCreate(id: String,flag:String)=chatRepository.createChat(id, flag)
    fun userSelectionList()=selectionRepository.userSelectionList()
    fun deleteUserSelection(id:String)=selectionRepository.deleteUserSelection(id)
    fun addToSelection(id:String,product:String)=selectionRepository.addProductToSelection(id, ProductsSelection(products = listOf(product)))
    fun getPersonalProducts()=productsRepository.getPersonalProducts()
    fun addToComparison(id: String)=comparisonRepository.addToComparison(listOf(
        ComparisonProductData(id)
    ))
    fun deletePersonalProduct(id:String)=productsRepository.deletePersonalProduct(id)


}