package com.expostore.ui.fragment.favorites

import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.SearchRepository
import com.expostore.data.repositories.SelectionRepository
import javax.inject.Inject

class FavoritesInteractor @Inject constructor(private val favoriteRepository: FavoriteRepository,
                                              private val chatRepository: ChatRepository,
                                              private val searchRepository: SearchRepository,
                                               private val selectionRepository: SelectionRepository) {

    fun getProductFavoriteList()=favoriteRepository.getFavoritesList()
    fun getTenderFavoriteList()=favoriteRepository.getFavoriteTenderList()
    fun updateSelected(id:String)=favoriteRepository.updateSelected(id)
    fun updateSelectedTender(id:String)=favoriteRepository.updateSelectedTender(id)
    fun getSearchList()=searchRepository.saveSearchList()
    fun deleteSaveSearch(id:String)=searchRepository.deleteSaveSearch(id)
    fun chatCreate(id: String,flag:String)=chatRepository.createChat(id, flag)
    fun userSelectionList()=selectionRepository.userSelectionList()
    fun deleteUserSelection(id:String)=selectionRepository.deleteUserSelection(id)

}