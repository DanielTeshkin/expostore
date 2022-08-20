package com.expostore.ui.fragment.category

import androidx.lifecycle.viewModelScope
import com.expostore.api.pojo.getchats.ChatResponse
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.ChatCreateRequest
import com.expostore.api.request.ProductsSelection
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.data.repositories.SelectionRepository
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.profile.ProfileModel

import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.state.ResponseState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailCategoryViewModel @Inject constructor(private val profileRepository: ProfileRepository,
                                                  private val favoriteRepository: FavoriteRepository,
                                                  private val chatRepository: ChatRepository,
                                                  private val selectionRepository: SelectionRepository
) : BaseViewModel() {
    private val _selection=MutableStateFlow(SelectionModel())
    val selection=_selection.asStateFlow()
    private val _author=MutableStateFlow("")
    val author=_author.asStateFlow()
    private val chat=MutableSharedFlow<ResponseState<ChatResponse>>()
    private val select=MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    private val profile =MutableSharedFlow<ProfileModel>()
    private val _selections=MutableSharedFlow<ResponseState<List<SelectionModel>>>()
    val selections=_selections.asSharedFlow()
    private val chatUI= MutableSharedFlow<ResponseState<MainChat>>()
 val flag= MutableStateFlow(false)

    override fun onStart() {
        selectionList()
    }
    fun saveSelection(selectionModel: SelectionModel){
        _selection.value=selectionModel
    }
    private fun selectionList()=selectionRepository.getSelections().handleResult(_selections)

    fun updateSelected(id:String)=favoriteRepository.updateSelected(id).handleResult(select)




    fun createChat(id: String) =chatRepository.createChat(id,"product").handleResult(chatUI,{
        val result = InfoItemChat(
            it.identify()[1],
            it.identify()[0],
            it.chatsId(),
            it.imagesProduct(),
            it.productsName(), it.identify()[3]
        )
        navigateToChat(result)
    })
    fun addToSelection(id:String,product:String)=selectionRepository.addProductToSelection(id,
        ProductsSelection(listOf(product))
    ).handleResult()

    fun navigateToNote()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToNoteFragment())
    fun navigateToBlock()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSupportFragment())
    fun navigateToCreateSelection()=navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToSelectionCreate())


    fun navigateToProduct(product:ProductModel){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToProductFragment(product))
    }
  private fun navigateToChat(infoItemChat: InfoItemChat){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToChatFragment(infoItemChat))
    }


}