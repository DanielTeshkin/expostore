package com.expostore.ui.fragment.favorites.tabs.personal

import com.expostore.model.category.SelectionModel

interface OnClickCategory {
    fun onClickSelection(selectionModel:SelectionModel,flag:String)
    fun onClickLike(id:String)
}
