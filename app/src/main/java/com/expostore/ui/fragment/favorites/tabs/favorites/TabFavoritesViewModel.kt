package com.expostore.ui.fragment.favorites.tabs.favorites


import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.response.NoteResponse
import com.expostore.data.remote.api.response.SelectionResponse
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseProductInteractor
import com.expostore.ui.base.BaseProductViewModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.category.DetailCategoryFragmentDirections
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TabFavoritesViewModel @Inject constructor(private val favoriteInteractor: BaseProductInteractor) : BaseProductViewModel() {
    private val _favoriteList= MutableSharedFlow<ResponseState<List<FavoriteProduct>>>()
   val favoriteList=_favoriteList.asSharedFlow()
    private val _delete=MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    val  delete=_delete.asSharedFlow()
    private val _state= MutableStateFlow<Boolean>(true)
    val state=_state.asStateFlow()


 init {
  interactor=favoriteInteractor
     loadFavoriteList()
     getSelections()
 }
    override fun onStart() {

    }
    fun loadFavoriteList()= interactor?.getProductFavoriteList()?.handleResult(_favoriteList)

    fun delete(){
        onCleared()
    }

    override fun navigateToChat(result: InfoItemChat) =
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToChatFragment(result))

  override  fun navigateToNote(model: ProductModel) =navigationTo(DetailCategoryFragmentDirections
        .actionDetailCategoryFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "product", flagNavigation = "product"))
    override fun navigateToBlock()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSupportFragment())
    override  fun navigateToCreateSelection(product: String)=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSelectionCreate())
    override fun navigateToProduct(product: ProductModel){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToProductFragment(product))
    }



}