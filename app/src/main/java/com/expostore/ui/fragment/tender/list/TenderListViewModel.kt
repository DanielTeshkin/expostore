package com.expostore.ui.fragment.tender.list

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.api.request.ChatCreateRequest
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.data.repositories.TenderRepository
import com.expostore.ui.fragment.search.filter.models.FilterModel

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TenderListViewModel @Inject constructor( private val interactor: TenderInteractor) : BaseViewModel() {
       private val _tenders=MutableSharedFlow<ResponseState<List<TenderModel>>>()
        val tenders=_tenders.asSharedFlow()
    private val _state =MutableStateFlow<Boolean>(true)
    val state=_state.asStateFlow()
    private val _sort=MutableStateFlow<String?>(null)
    val sort=_sort.asStateFlow()
    private val token=MutableStateFlow<String?>(null)
    private val select = MutableSharedFlow<ResponseState<SelectFavoriteTenderResponseData>>()
    fun createTender(){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToTenderCreateFragment())
    }

    fun selectFavorite(id: String) = interactor.selectFavorite(id).handleResult(select)
 val tender=interactor.letTenderFlow().cachedIn(viewModelScope)
    fun newSort(){
        if(_sort.value==null){
        _sort.value="-date_created"}
        else{ _sort.value=null}
    }
    fun navigateToItem(){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToTenderItem())
    }

   private fun navigateToOpen(){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToOpenFragment())
    }
    fun createChat(tender:String) = interactor.chatCreate(tender)
    fun navigateToChat(){
        navigationTo(TenderListFragmentDirections.actionTenderListFragmentToChatFragment())
    }

    fun changeState(state:Boolean){
        this._state.value=state
    }
    fun loadTenderList() = interactor.letTenderFlow().cachedIn(viewModelScope)

    fun loadTenderListWithFilters(filterModel: FilterModel) = interactor.letTenderFlow(filterModel = filterModel).cachedIn(viewModelScope)

     fun navigateToFilter(){
         navigationTo(TenderListFragmentDirections.actionTenderListFragmentToSearchFilterFragment())
     }

        fun check(){
            if(token.value.isNullOrEmpty())navigateToOpen()
        }


    override fun onStart() {

    }
}