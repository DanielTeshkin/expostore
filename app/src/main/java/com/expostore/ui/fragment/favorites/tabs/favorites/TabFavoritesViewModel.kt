package com.expostore.ui.fragment.favorites.tabs.favorites


import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseProductInteractor
import com.expostore.ui.base.BaseProductViewModel
import com.expostore.ui.fragment.category.DetailCategoryFragmentDirections
import com.expostore.ui.fragment.favorites.FavoritesFragmentDirections
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
    private val _state= MutableStateFlow(true)
    val state=_state.asStateFlow()

    init {
  interactor=favoriteInteractor
        loadFavoriteList()
        getSelections()
    }
    override fun onStart() {

    }
    private fun loadFavoriteList()= interactor?.getProductFavoriteList()?.handleResult(_favoriteList)

    fun delete()= onCleared()
    override fun navigateToChat(infoItemChat: InfoItemChat) =
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToChatFragment(infoItemChat))

  override  fun navigateToNote(model: ProductModel) =navigationTo(FavoritesFragmentDirections
        .actionFavoritesFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected.notes, flag = "product", flagNavigation = "product"))
    override fun navigateToBlock()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSupportFragment())
    override  fun navigateToCreateSelection(product: String)=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSelectionCreate())
    override fun navigateToProduct(product: ProductModel)=
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment2(product))




}