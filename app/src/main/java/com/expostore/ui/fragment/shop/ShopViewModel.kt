package com.expostore.ui.fragment.shop

import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.getshop.GetShopResponseData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.repositories.*
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
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val shopInteractor: BaseProductInteractor) : BaseProductViewModel() {

    private val _shop=MutableSharedFlow<ResponseState<GetShopResponseData>>()
    val shop=_shop.asSharedFlow()
    override fun onStart() {
        }
    init {
        interactor=shopInteractor
    }
    fun getShop(id:String)=interactor?.getShop(id)?.handleResult(_shop)
   override fun navigateToProduct(model:ProductModel){
        navigationTo(ShopFragmentDirections.actionShopFragmentToProductFragment(model))
    }
   override fun navigateToCreateSelection(product: String) {
       navigationTo(ShopFragmentDirections.actionShopFragmentToSelectionCreate(product))
    }
   override fun navigateToNote(model: ProductModel) {
        navigationTo(ShopFragmentDirections.actionShopFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "product", flagNavigation = "product"))
    }

    override fun navigateToChat(infoItemChat: InfoItemChat) {
        navigationTo(ShopFragmentDirections.actionShopFragmentToChatFragment(infoItemChat))
    }



}