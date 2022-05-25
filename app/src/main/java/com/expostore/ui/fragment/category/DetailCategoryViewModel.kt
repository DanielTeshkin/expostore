package com.expostore.ui.fragment.category

import androidx.lifecycle.viewModelScope
import com.expostore.api.pojo.getchats.ChatResponse
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.ChatCreateRequest
import com.expostore.data.repositories.ChatRepository
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.model.category.SelectionModel
import com.expostore.model.profile.ProfileModel

import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailCategoryViewModel @Inject constructor(private val profileRepository: ProfileRepository,
                                                  private val favoriteRepository: FavoriteRepository,
                                                  private val chatRepository: ChatRepository
) : BaseViewModel() {
    private val _selection=MutableStateFlow(SelectionModel())
    val selection=_selection.asStateFlow()
    private val _author=MutableStateFlow("")
    val author=_author.asStateFlow()
    private val chat=MutableSharedFlow<ResponseState<ChatResponse>>()
    private val select=MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    private val profile =MutableSharedFlow<ProfileModel>()

    fun saveSelection(selectionModel: SelectionModel){
        _selection.value=selectionModel
    }

    fun updateSelected(id:String)=favoriteRepository.updateSelected(id).handleResult(select)


    fun getProfile(){
        //viewModelScope.launch{
            //withContext(Dispatchers.IO) {
             //   profileRepository.getProfile().collect {
              //          _author.value=it.id?:""
               // }
           // }
       // }
    }

    fun createChat(id: String) =chatRepository.createChat(id,"product")


    fun navigateToProduct(){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToProductFragment())
    }
  fun navigateToChat(){
        navigationTo(DetailCategoryFragmentDirections.actionDetailCategoryFragmentToChatFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}