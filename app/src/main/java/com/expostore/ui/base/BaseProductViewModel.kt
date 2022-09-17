package com.expostore.ui.base

import androidx.navigation.NavDirections
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.ComparisonRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.SelectionRepository
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.category.DetailCategoryFragmentDirections
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

 open class BaseProductViewModel : BaseViewModel() {
   protected open var interactor:BaseItemsInteractor<ProductModel, SelectFavoriteResponseData, FavoriteProduct>?=null
   private val _chat= MutableSharedFlow<ResponseState<MainChat>>()
    private val chatData= MutableStateFlow(InfoItemChat())
   open var flag:String="product"
    private val _selections=MutableSharedFlow<ResponseState<List<SelectionModel>>>()
     val selections= _selections.asSharedFlow()
    override fun onStart() {

    }

     fun getSelections()= interactor?.getSelections()?.handleResult(_selections)

    fun createChat(id:String)=interactor?.createChat(id,flag)?.handleResult(_chat,{
        updateChatData(it)
        navigateToChat(chatData.value)
    })

    private fun updateChatData(chat:MainChat) = chat.apply {
        chatData.value = InfoItemChat(identify()[1],identify()[0],chatsId(),imagesProduct(),productsName(),identify()[3])
    }
     fun updateSelected(id:String)=interactor?.updateSelected(id)?.handleResult()
     fun comparison(id: String)=interactor?.comparison(id)?.handleResult()
     fun addToSelection(id:String,product:String)=interactor?.addToSelection(id,
         product)?.handleResult()

    // fun delete()=

   open fun navigateToNote(model: ProductModel){}
   open fun navigateToBlock(){}
   open fun navigateToCreateSelection(product: String){}
   open fun navigateToProduct(product: ProductModel){}
  open fun navigateToChat(infoItemChat: InfoItemChat){}

 }