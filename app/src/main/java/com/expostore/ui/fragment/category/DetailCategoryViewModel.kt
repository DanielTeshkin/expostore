package com.expostore.ui.fragment.category

import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.base.vms.BaseProductViewModel
import com.expostore.ui.fragment.category.personal.DetailPersonalSelectionFragmentDirections

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailCategoryViewModel @Inject constructor(override val interactor: BaseProductInteractor) : BaseProductViewModel() {
    private val _selectionModel=MutableStateFlow(SelectionModel())
    val selectionModel=_selectionModel.asStateFlow()
    init {
        getSelections()
    }
    fun saveSelection(selectionModel: SelectionModel){
        _selectionModel.value=selectionModel
    }
    override fun navigateToBlock()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSupportFragment())
    override  fun navigateToCreateSelection(product: String) =navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSelectionCreate(id=product))
    override fun navigateToComparison() {
        TODO("Not yet implemented")
    }

    override fun navigateToItem(model:ProductModel){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToProductFragment(model))
    }

    override fun navigateToNote(model: ProductModel){
        navigationTo(
            DetailCategoryFragmentDirections.actionDetailCategoryFragmentToNoteFragment(id=model.id,
            isLiked = model.isLiked, text = model.elected.notes, flag = "product", flagNavigation = "product"))
    }

    override  fun navigateToChat(infoItemChat: InfoItemChat)= navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToChatFragment(infoItemChat))



}