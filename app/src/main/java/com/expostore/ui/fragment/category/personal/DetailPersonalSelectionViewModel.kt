package com.expostore.ui.fragment.category.personal

import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.request.ProductsSelection
import com.expostore.data.remote.api.request.SelectionRequest
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.ComparisonRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.SelectionRepository
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseProductInteractor
import com.expostore.ui.base.BaseProductViewModel
import com.expostore.ui.base.BaseViewModel
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
@HiltViewModel
class DetailPersonalSelectionViewModel @Inject constructor(private val interactorSelection: BaseProductInteractor) : BaseProductViewModel() {
    val selection= MutableStateFlow(SelectionModel())
    init {
        interactor=interactorSelection
        getSelections()
    }
    fun save(selectionModel: SelectionModel){
        selection.value=selectionModel
    }
    fun navigateToEdit()=navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToSelectionCreate(
        selection = selection.value))

    fun delete(product: ProductModel)=
        interactorSelection.deleteProductFromSelection(product,selection.value.products,selection.value.id,selection.value.name)
            .handleResult()

               override fun navigateToChat(infoItemChat: InfoItemChat) =navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToChatFragment(infoItemChat))
    override fun onStart() {

    }
    fun deleteSelection() = interactorSelection.deleteSelection(selection.value.id).handleResult({},
        {
            navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToFavoritesFragment())
        }

    )


    override fun navigateToProduct(product: ProductModel){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToProductFragment(product))
    }
    override fun navigateToNote(model: ProductModel){
        navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected?.notes, flag = "product", flagNavigation = "product"))
    }

    override fun navigateToCreateSelection(product: String) =navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSelectionCreate(id=product))


}