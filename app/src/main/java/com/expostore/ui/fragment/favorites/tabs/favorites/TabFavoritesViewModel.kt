package com.expostore.ui.fragment.favorites.tabs.favorites


import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.base.vms.BaseProductViewModel
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
class TabFavoritesViewModel @Inject constructor(override val interactor: BaseProductInteractor) : BaseProductViewModel() {

    private val _delete=MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    val  delete=_delete.asSharedFlow()
    private val _state= MutableStateFlow(true)
    val state=_state.asStateFlow()
    override fun onStart() {

    }
    fun delete()= onCleared()
    override fun navigateToChat(value: InfoItemChat) =
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToChatFragment(value))

  override  fun navigateToNote(model: ProductModel) =navigationTo(FavoritesFragmentDirections
        .actionFavoritesFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected.notes, flag = "product", flagNavigation = ""))
    override fun navigateToBlock()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSupportFragment())
    override fun navigateToItem(model: ProductModel) = navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment2(model))

    override  fun navigateToCreateSelection(product: String)=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSelectionCreate())
    override fun navigateToComparison() =navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToComparison())






}