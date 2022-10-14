package com.expostore.ui.fragment.category.personal

import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.base.vms.BaseProductViewModel
import com.expostore.ui.fragment.category.DetailCategoryFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
@HiltViewModel
class DetailPersonalSelectionViewModel
@Inject constructor(override val interactor: BaseProductInteractor) : BaseProductViewModel() {
    val selection= MutableStateFlow(SelectionModel())
    init {
        getSelections()
    }
    fun save(selectionModel: SelectionModel){
        selection.value=selectionModel
    }
    fun navigateToEdit()=navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToSelectionCreate(
        selection = selection.value))

    fun delete(product: ProductModel)=
        interactor.deleteProductFromSelection(product,selection.value.products,selection.value.id,selection.value.name)
            .handleResult()

               override fun navigateToChat(value: InfoItemChat) =navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToChatFragment(value))
    override fun navigateToBlock() {
        TODO("Not yet implemented")
    }

    override fun onStart() {

    }
    fun deleteSelection() = interactor.deleteSelection(selection.value.id).handleResult({},
        {
            navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToFavoritesFragment())
        }

    )


    override fun navigateToItem(model: ProductModel){
        navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToProductFragment(model))
    }
    override fun navigateToNote(model: ProductModel){
        navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected.notes, flag = "product", flagNavigation = ""))
    }

    override fun navigateToOpen(){}

    override fun navigateToCreateSelection(product: String) =
        navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToSelectionCreate(id=product))
    override fun navigateToComparison() =navigationTo(DetailPersonalSelectionFragmentDirections.actionPersonalSelectionToComparison())


}