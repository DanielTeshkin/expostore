package com.expostore.ui.fragment.favorites.tabs.selections

import android.text.Selection
import com.expostore.model.category.SelectionModel

interface OnClickCategory {
    fun onClickSelection(selectionModel:SelectionModel,flag:String)
    fun onClickLike(id:String)
}
