package com.expostore.ui.fragment.product

import com.expostore.data.repositories.ChatRepository
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(private val chatRepository: ChatRepository) : BaseViewModel() {
    private val _product=MutableStateFlow(ProductModel())
    val product=_product.asStateFlow()
    private val _chatUI=MutableSharedFlow<ResponseState<MainChat>>()
    val chatUI=_chatUI.asSharedFlow()


    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun saveProduct(item:ProductModel){
        _product.value=item
    }
   private fun navigationChat(result: InfoItemChat) {
          navigationTo(ProductFragmentDirections.actionProductFragmentToChatFragment(result))
    }
    fun navigationToReview(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToReviewsFragment())
    }
  fun navigationToShop(){
      navigationTo(ProductFragmentDirections.actionProductFragmentToShopFragment())
  }
    fun navigationToAddReview(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToAddReviewFragment())
    }
    fun createChat(id:String,flag:String) = chatRepository.createChat(id,flag).handleResult(_chatUI,{chat->
        val result = InfoItemChat(
            chat.identify()[1],
            chat.identify()[0],
            chat.chatsId(),
            chat.imagesProduct(),
            chat.productsName(), chat.identify()[3]
        )
        navigationChat(result)
    })

    fun navigationToNote(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToNoteFragment())
    }
    fun navigationToQrCodeFragment(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToProductQrCodeFragment())
    }



}