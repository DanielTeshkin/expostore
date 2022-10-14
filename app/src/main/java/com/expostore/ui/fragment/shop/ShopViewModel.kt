package com.expostore.ui.fragment.shop

import com.expostore.data.remote.api.pojo.getshop.GetShopResponseData
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.base.vms.BaseProductViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(override val interactor: BaseProductInteractor) : BaseProductViewModel() {

    private val _shop=MutableSharedFlow<ResponseState<GetShopResponseData>>()
    val shop=_shop.asSharedFlow()
    override fun onStart() {
        }
    init {
        getSelections()
    }
    fun getShop(id:String)=interactor.getShop(id).handleResult(_shop)

   override fun navigateToCreateSelection(product: String) {
       navigationTo(ShopFragmentDirections.actionShopFragmentToSelectionCreate(product))
    }

    override fun navigateToComparison()=navigationTo(ShopFragmentDirections.actionShopFragmentToComparison())

    override fun navigateToNote(model: ProductModel) {
        navigationTo(ShopFragmentDirections.actionShopFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "product", flagNavigation = ""))
    }

    override fun navigateToOpen()=navigationTo(ShopFragmentDirections.actionShopFragmentToOpenFragment())
    override fun navigateToChat(value: InfoItemChat) = navigationTo(ShopFragmentDirections.actionShopFragmentToChatFragment(value))


    override fun navigateToBlock() {
        TODO("Not yet implemented")
    }

    override fun navigateToItem(model: ProductModel) = navigationTo(ShopFragmentDirections.actionShopFragmentToProductFragment(model))


}