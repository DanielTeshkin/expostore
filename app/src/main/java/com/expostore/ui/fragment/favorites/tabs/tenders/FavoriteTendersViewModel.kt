package com.expostore.ui.fragment.favorites.tabs.tenders

import androidx.lifecycle.ViewModel
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.model.favorite.FavoriteTender
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteTendersViewModel @Inject constructor(private val interactor: FavoritesInteractor) : BaseViewModel() {
    private val _tenders= MutableSharedFlow<ResponseState<List<FavoriteTender>>>()
    val tenders=_tenders.asSharedFlow()
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun getTenderFavoriteList()=interactor.getTenderFavoriteList().handleResult(_tenders)
    fun  updateSelectedTender(id:String)=interactor.updateSelectedTender(id).handleResult()
    fun createChat(id: String,flag:String)=interactor.chatCreate(id,flag).handleResult()
}