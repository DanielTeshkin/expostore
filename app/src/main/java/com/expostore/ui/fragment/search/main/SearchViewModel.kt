package com.expostore.ui.fragment.search.main

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseItemsInteractor

import com.expostore.ui.base.vms.BaseProductViewModel
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.fragment.search.filter.models.FilterModel

import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(override var interactor: BaseProductInteractor) :BaseProductViewModel() {
    private val myLat = MutableStateFlow(0.0)
    private val myLong = MutableStateFlow(0.0)
    private val _city = MutableStateFlow("Москва")
   val city = _city.asStateFlow()
    private val _result = MutableSharedFlow<PagingData<ProductModel>>()
    val result = _result.asSharedFlow()
    private val _author = MutableStateFlow("")
    val author = _author.asStateFlow()
    private val _productsMarkerUI= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    override fun onStart() {
    }
    init {
        getSelections()
    }

     fun getBaseProducts()=interactor.getBaseListProducts()
    fun saveLocation(latitude: Double, longitude: Double) {
        myLat.value = latitude
        myLong.value = longitude
    }

    override fun navigateToBlock(){}
    override fun navigateToItem(model: ProductModel) = navigationTo(SearchFragmentDirections.actionSearchFragmentToProductFragment(model))
    fun navigateToFilter() = navigationTo(SearchFragmentDirections.actionSearchFragmentToSearchFilterFragment())
    override fun navigateToChat(info:InfoItemChat)= navigationTo(SearchFragmentDirections.actionSearchFragmentToChatFragment(info))
    override fun navigateToCreateSelection(id: String){
        navigationTo(SearchFragmentDirections.actionSearchFragmentToSelectionCreate(id))
    }
    override fun navigateToComparison()=navigationTo(SearchFragmentDirections.actionSearchFragmentToComparison())
    override fun navigateToNote(model: ProductModel) {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected.notes, flag = "product", flagNavigation = ""))
    }

    override fun navigateToOpen() =navigationTo(SearchFragmentDirections.actionSearchFragmentToOpenFragment())
}