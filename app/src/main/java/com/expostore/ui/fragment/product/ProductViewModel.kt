package com.expostore.ui.fragment.product

import android.util.Log
import androidx.core.os.bundleOf
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.PriceHistoryDataModel
import com.expostore.model.product.PriceHistoryModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.base.vms.BaseProductViewModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.product.utils.CharacteristicsData
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(override val interactor: BaseProductInteractor) : BaseProductViewModel() {
    private val _product=MutableStateFlow(ProductModel())
    val product=_product.asStateFlow()

    private val _priceHistoryState= MutableSharedFlow<ResponseState<List<PriceHistoryModel>>>()
           private val priceHistoryList= MutableStateFlow<List<PriceHistoryModel>>(listOf())
    private val _visible = MutableStateFlow(false)
    val visible=_visible.asStateFlow()
    private val  _visibleInstruction= MutableStateFlow(false)
    private val _visiblePresentation = MutableStateFlow(false)
      val visibleInstruction=_visibleInstruction.asStateFlow()
    val visiblePresentation=_visiblePresentation.asStateFlow()
    override fun navigateToComparison() {
        TODO("Not yet implemented")
    }

    override fun navigateToCreateSelection(product: String) {
        TODO("Not yet implemented")
    }
    fun getProduct(id:String)=interactor.getProduct(id).handleResult({},{ _product.value=it })

    override fun navigateToChat(value: InfoItemChat) {
     navigationTo(ProductFragmentDirections.actionProductFragmentToChatFragment(value))
    }

    override fun navigateToBlock() {
        TODO("Not yet implemented")
    }

    override fun navigateToItem(model: ProductModel) {
        TODO("Not yet implemented")
    }

    override fun navigateToNote(model: ProductModel) {
        navigationTo(ProductFragmentDirections.actionProductFragmentToNoteFragment(id=product.value.id,
            text = product.value.elected.notes,flag = "product", isLiked = product.value.isLiked, flagNavigation = "product"))
    }

    fun navigateToBack()=navController.popBackStack()
    fun navSave()=navController.previousBackStackEntry?.saveState(bundleOf( "iem" to SelectionModel()))


    fun saveProduct(item:ProductModel){
        _product.value=item
        interactor.getPriceHistory(item.id).handleResult(_priceHistoryState,{
            priceHistoryList.value=it
            _visible.value=it.isNotEmpty()
        })
        _visibleInstruction.value=item.instruction.file.isNotEmpty()
        _visiblePresentation.value=item.presentation.file.isNotEmpty()
    }

    fun navigationToReview(model:ReviewsModel){
        navigationTo(ProductFragmentDirections.actionProductFragmentToReviewsFragment(flag = "list", model ))
    }
    fun navigateToPriceHistory()=navigationTo(ProductFragmentDirections
        .actionProductFragmentToPriceHistory(PriceHistoryDataModel(priceHistoryList.value)))
  fun navigationToShop(){
      navigationTo(ProductFragmentDirections.actionProductFragmentToShopFragment())
  }
    fun navigationToAddReview(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToAddReviewFragment())
    }
    fun navigateToInstruction()=
        navigationTo(ProductFragmentDirections.actionProductFragmentToWebViewFragment(product.value.instruction.file))

    fun navigateToPresentation()=
        navigationTo(ProductFragmentDirections.actionProductFragmentToWebViewFragment(product.value.presentation.file))

    fun navigationToNote(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToNoteFragment(id=product.value.id,
            text = product.value.elected?.notes,flag = "product", isLiked = product.value.isLiked, flagNavigation = "product"))
    }
    fun navigationToQrCodeFragment(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToProductQrCodeFragment())
    }
    fun navigateToCharacteristics()=navigationTo(ProductFragmentDirections
        .actionProductFragmentToCharacteristicFragment(CharacteristicsData(product.value.characteristics)))



}