package com.expostore.ui.fragment.search.main

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseProductInteractor
import com.expostore.ui.base.BaseProductViewModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.search.filter.models.FilterModel

import com.expostore.ui.fragment.search.main.interactor.SearchInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(override var interactor: BaseProductInteractor?) :
    BaseProductViewModel() {

    private val myLat = MutableStateFlow(0.0)
    private val myLong = MutableStateFlow(0.0)
    private val _city = MutableStateFlow("Москва")
   val city = _city.asStateFlow()
    private val _result = MutableSharedFlow<PagingData<ProductModel>>()
    val result = _result.asSharedFlow()
    private val _author = MutableStateFlow("")
    val author = _author.asStateFlow()
    private val _selectionList= MutableSharedFlow<ResponseState<List<SelectionModel>>>()
    val selectionList=_selectionList.asSharedFlow()
    val search = interactor?.letProductFlow()?.cachedIn(viewModelScope)
    private val _productsMarkerUI= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val productsMarkerUI=_productsMarkerUI.asSharedFlow()

    override fun onStart() {
    }
    init {
        getSelections()
    }

     fun getBaseProducts()=interactor?.getBaseListProducts()
    fun saveLocation(latitude: Double, longitude: Double) {
        myLat.value = latitude
        myLong.value = longitude
    }

    fun searchWithFilters(filterModel: FilterModel) = interactor?.searchProducts(filterModel = filterModel)?.cachedIn(viewModelScope)

    override fun navigateToProduct(productModel: ProductModel) {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToProductFragment(productModel))
    }
   override fun navigateToBlock(){}

   fun navigateToFilter() = navigationTo(SearchFragmentDirections.actionSearchFragmentToSearchFilterFragment())

  override fun navigateToChat(info:InfoItemChat)= navigationTo(SearchFragmentDirections.actionSearchFragmentToChatFragment(info))

  override fun navigateToCreateSelection(id: String){
        navigationTo(SearchFragmentDirections.actionSearchFragmentToSelectionCreate(id))
    }
   override fun navigateToNote(model: ProductModel) {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "product", flagNavigation = "product"))
    }
    }