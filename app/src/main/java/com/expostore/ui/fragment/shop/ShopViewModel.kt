package com.expostore.ui.fragment.shop

import androidx.lifecycle.ViewModel
import com.expostore.api.pojo.getchats.ChatResponse
import com.expostore.api.pojo.getshop.GetShopResponseData
import com.expostore.api.request.ProductsSelection
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.SelectionRepository
import com.expostore.data.repositories.ShopRepository
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val shopRepository: ShopRepository,private val favoriteRepository: FavoriteRepository,
                                       private val chatRepository: ChatRepository,
private val selectionRepository: SelectionRepository) : BaseViewModel() {

    private val _shop=MutableSharedFlow<ResponseState<GetShopResponseData>>()
    val shop=_shop.asSharedFlow()
    private val _chatCreateUI= MutableSharedFlow<ResponseState<MainChat>>()
    val chatCreateUI=_chatCreateUI.asSharedFlow()
    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun getShop(id:String)=shopRepository.getShop(id).handleResult(_shop)

    fun createChat(id: String)=chatRepository.createChat(id,"product").handleResult(_chatCreateUI, {
        val result = InfoItemChat(
            it.identify()[1],
            it.identify()[0],
            it.chatsId(),
            it.imagesProduct(),
            it.productsName(), it.identify()[3]
        )
        navigationTo(ShopFragmentDirections.actionShopFragmentToChatFragment(result))
    })
    fun updateSelected(id: String)=favoriteRepository.updateSelected(id).handleResult()
    fun getSelections()=selectionRepository.userSelectionList()
    fun addToSelection(id: String,product:String)=selectionRepository.addProductToSelection(id,
        ProductsSelection(listOf(product))).handleResult()
    fun navigateToProduct(model:ProductModel){
        navigationTo(ShopFragmentDirections.actionShopFragmentToProductFragment(model))
    }
    fun navigateToCreateSelection(){
       navigationTo(ShopFragmentDirections.actionShopFragmentToSelectionCreate())
    }
    fun navigateToNote(){
        navigationTo(ShopFragmentDirections.actionShopFragmentToNoteFragment())
    }

}