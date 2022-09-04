package com.expostore.ui.fragment.category

import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.getchats.ChatResponse
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.repositories.*
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.base.BaseProductInteractor
import com.expostore.ui.base.BaseProductViewModel

import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.state.ResponseState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailCategoryViewModel @Inject constructor(private val detailInteractor: BaseProductInteractor) : BaseProductViewModel() {
    private val _selectionModel=MutableStateFlow(SelectionModel())
    val selectionModel=_selectionModel.asStateFlow()

    override fun onStart() {
    }
    init {
        interactor=detailInteractor
        getSelections()
    }
    fun saveSelection(selectionModel: SelectionModel){
        _selectionModel.value=selectionModel
    }
    override fun navigateToBlock()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSupportFragment())
    override  fun navigateToCreateSelection(product: String) =navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSelectionCreate(id=product))
    override fun navigateToProduct(product:ProductModel){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToProductFragment(product))
    }
    override  fun navigateToChat(infoItemChat: InfoItemChat)= navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToChatFragment(infoItemChat))



}