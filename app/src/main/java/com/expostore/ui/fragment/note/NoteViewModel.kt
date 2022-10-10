package com.expostore.ui.fragment.note

import android.util.Log
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.data.remote.api.request.NoteRequest
import com.expostore.data.repositories.FavoriteRepository
import com.expostore.data.repositories.ProductsRepository
import com.expostore.data.repositories.TenderRepository
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor( private val favoriteRepository: FavoriteRepository,
                                         private val productsRepository: ProductsRepository,
                                           private val tenderRepository: TenderRepository) : BaseViewModel() {
    private val data=MutableStateFlow(NoteData())
    private val select = MutableSharedFlow<ResponseState<SelectFavoriteResponseData>>()
    private val selectTender = MutableSharedFlow<ResponseState<SelectFavoriteTenderResponseData>>()
    override fun onStart() {
       Log.i("fff","ff")
    }

    fun createOrUpdateNoteProduct(note:String) {
        if (data.value.flag=="product"&&data.value.isLiked==false) favoriteRepository.addToFavorite(data.value.id?:"",
            NoteRequest(text = note)
        ).handleResult(select,{
            navigate(it.product?:"")
        })
        else if (data.value.flag=="tender"&&data.value.isLiked==false) favoriteRepository.addToFavoriteTender(data.value.id?:"",NoteRequest(text = note)).handleResult(selectTender,{
            navigate(it.tender?:"")
        })
        else if(data.value.flag=="product"&&data.value.isLiked==true) favoriteRepository.addNoteProduct(data.value.id?:"",NoteRequest(text = note)).handleResult(select,{
            navigate(it.product?:"")
        })
        else if(data.value.flag=="tender"&&data.value.isLiked==true) favoriteRepository.addNoteTender(data.value.id?:"",NoteRequest(text = note)).handleResult(selectTender,{
            navigate(it.tender?:"")
        })

    }

    fun navigate(id: String) {
        when (data.value.flagNavigation) {
            "product" -> {
                productsRepository.getProduct(data.value.id?:"").handleResult({},{
                    Log.i("prod",it.id)
                    navigationTo(NoteFragmentDirections.actionNoteFragmentToProductFragment(it))
                },{
                    Log.i("error",it.message?:"")
                })

            }
            "tender" -> {
                tenderRepository.getTender(data.value.id?:"").handleResult({},{
                    navigationTo(NoteFragmentDirections.actionNoteFragmentToTenderItem(it))
                })

            }
            "personal"-> {
                productsRepository.getPersonalProduct(data.value.id?:"").handleResult({},{
                    navigationTo(NoteFragmentDirections.actionNoteFragmentToPersonalProductfragment(it))
                })

            }
            else -> navController.popBackStack()
        }
    }

    fun saveData(noteData: NoteData){
        data.value=noteData
    }

}