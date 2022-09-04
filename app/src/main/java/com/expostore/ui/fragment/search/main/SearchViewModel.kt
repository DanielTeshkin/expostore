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
class SearchViewModel @Inject constructor(private val interactor: SearchInteractor) :
    BaseViewModel() {

    private val _searchResult = MutableSharedFlow<ResponseState<PagingData<ProductModel>>>()
    val searchResult = _searchResult.asSharedFlow()
    private val myLat = MutableStateFlow<Double>(0.0)
    private val myLong = MutableStateFlow<Double>(0.0)
    private val _city = MutableStateFlow<String>("Москва")
   val city = _city.asStateFlow()
    private val _result = MutableSharedFlow<PagingData<ProductModel>>()
    val result = _result.asSharedFlow()
    private val _author = MutableStateFlow("")
    val author = _author.asStateFlow()
    private val select = MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    private val product=MutableSharedFlow<ResponseState<List<ProductResponse>>>()
    private val _selectionList= MutableSharedFlow<ResponseState<List<SelectionModel>>>()
    val selectionList=_selectionList.asSharedFlow()
    private val selectionModel=MutableSharedFlow<ResponseState<SelectionResponse>>()
    val search = interactor.letProductFlow().cachedIn(viewModelScope)
    private val chatUi=MutableSharedFlow<ResponseState<MainChat>>()
    private val _productsMarkerUI= MutableSharedFlow<ResponseState<List<ProductModel>>>()
    val productsMarkerUI=_productsMarkerUI.asSharedFlow()

    override fun onStart() {


    }
     fun getSelections() = interactor.getPersonalSelections().handleResult(_selectionList)
     fun getBaseProducts()=interactor.getBaseListProducts()


    fun saveLocation(latitude: Double, longitude: Double) {
        myLat.value = latitude
        myLong.value = longitude
    }


    fun searchWithFilters(filterModel: FilterModel) = interactor.searchProducts(filterModel = filterModel).cachedIn(viewModelScope)


    fun addToSelection(id: String,product:String)=interactor.addToSelection(id, product).handleResult(selectionModel)

    fun createChat(id: String) = interactor.createChat(id).handleResult(chatUi,{
        val result = InfoItemChat(it.identify()[1], it.identify()[0], it.chatsId(), it.imagesProduct(), it.productsName(),
            it.identify()[3])
        navigateToChat(result)

    })

    fun selectFavorite(id: String) = interactor.selectFavorite(id).handleResult(select)

    fun addToComparison(id: String)=interactor.addToComparison(id).handleResult()

    fun navigateToProduct(productModel: ProductModel) {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToProductFragment(productModel))
    }
    fun navigateToBlock(){

    }

    fun navigateToFilter() {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToSearchFilterFragment())
    }
  private fun navigateToChat(info:InfoItemChat){
        navigationTo(SearchFragmentDirections.actionSearchFragmentToChatFragment(info))
    }
    fun navigateToSelectionCreate(id: String){
        navigationTo(SearchFragmentDirections.actionSearchFragmentToSelectionCreate(id))
    }
    fun navigateToNote(model: ProductModel) {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "product", flagNavigation = "product"))
    }




}