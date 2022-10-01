package com.expostore.ui.fragment.note

import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.data.remote.api.request.NoteRequest
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor( private val favoriteRepository: FavoriteRepository) : BaseViewModel() {
    private val data=MutableStateFlow(NoteData())
    private val select = MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    private val selectTender = MutableSharedFlow<ResponseState<SelectFavoriteTenderResponseData>>()
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun createOrUpdateNoteProduct(note:String) {
        if (data.value.flag=="product"&&data.value.isLiked==false) favoriteRepository.addToFavorite(data.value.id?:"",
            NoteRequest(text = note)
        ).handleResult(select,{
            navigate()
        })
        else if (data.value.flag=="tender"&&data.value.isLiked==false) favoriteRepository.addToFavoriteTender(data.value.id?:"",NoteRequest(text = note)).handleResult(selectTender,{
            navigate()
        })
        else if(data.value.flag=="product"&&data.value.isLiked==true) favoriteRepository.addNoteProduct(data.value.id?:"",NoteRequest(text = note)).handleResult(select,{
            navigate()
        })
        else if(data.value.flag=="tender"&&data.value.isLiked==true) favoriteRepository.addNoteTender(data.value.id?:"",NoteRequest(text = note)).handleResult(selectTender,{
            navigate()
        })

    }

    fun navigate(){
        when (data.value.flagNavigation) {
          //  "product" -> navigationTo(NoteFragmentDirections.actionNoteFragmentToProductFragment())
         //   "tender" -> navigationTo(NoteFragmentDirections.actionNoteFragmentToTenderItem())
        }
    }

    fun saveData(noteData: NoteData){
        data.value=noteData
    }

}