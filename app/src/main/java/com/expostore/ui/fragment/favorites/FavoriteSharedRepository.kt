package com.expostore.ui.fragment.favorites

import android.net.Uri
import com.expostore.model.category.SelectionModel
import kotlinx.coroutines.flow.MutableStateFlow

class FavoriteSharedRepository private constructor(){
    private val selections = MutableStateFlow(listOf<SelectionModel>())
    fun getSelections()=selections

    companion object {
        private val mInstance: FavoriteSharedRepository =
            FavoriteSharedRepository()

        @Synchronized
        fun getInstance():FavoriteSharedRepository {
            return mInstance
        }
    }
}