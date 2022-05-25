package com.expostore.ui.fragment.search.main

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.expostore.api.ServerApi
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.ChatCreateRequest
import com.expostore.api.request.ProductChat
import com.expostore.api.response.ProductResponse
import com.expostore.api.response.SelectionResponse
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.ProductsRepository
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.search.filter.models.FilterModel

import com.expostore.ui.fragment.search.main.interactor.SearchInteractor
import com.expostore.ui.state.ResponseState
import com.expostore.ui.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val _selectionList= MutableStateFlow<List<SelectionModel>>(mutableListOf())
    val selectionList=_selectionList.asStateFlow()
    private val selectionModel=MutableSharedFlow<ResponseState<SelectionResponse>>()



    val search = interactor.letProductFlow().cachedIn(viewModelScope)

    override fun onStart() {
      //  loadUserInfo()
    }
     fun getSelections() = interactor.getPersonalSelections()


    fun saveLocation(latitude: Double, longitude: Double) {
        myLat.value = latitude
        myLong.value = longitude
    }

    fun search(name: String) =
        interactor.searchProducts(name = name, lat = myLat.value, long = myLong.value)
            .sample(100)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)

    fun searchWithFilters(filterModel: FilterModel) =
        interactor.searchProducts(name = filterModel.name, lat = myLat.value, long = myLong.value, city = filterModel.city,
        priceMin = filterModel.price_min, priceMax = filterModel.price_max, category = filterModel.category, characteristics = filterModel.characteristics).cachedIn(viewModelScope)


    private fun loadUserInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val profileModel = interactor.getProfile()
                if (profileModel != null) {
                    _author.value = profileModel.id ?: ""
                    _city.value=profileModel.city?:"Москва"
                }
            }
        }
    }

    fun addToSelection(id: String,product:String)=interactor.addToSelection(id, product).handleResult(selectionModel)

    fun createChat(id: String,flag:String) = interactor.createChat(id, flag)

    fun selectFavorite(id: String) = interactor.selectFavorite(id).handleResult(select)

    fun navigateToProduct() {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToProductFragment())
    }

    fun navigateToFilter() {
        navigationTo(SearchFragmentDirections.actionSearchFragmentToSearchFilterFragment())
    }
  fun navigateToChat(){
        navigationTo(SearchFragmentDirections.actionSearchFragmentToChatFragment())
    }
    fun navigateToSelectionCreate(){
        navigationTo(SearchFragmentDirections.actionSearchFragmentToSelectionCreate())
    }




}