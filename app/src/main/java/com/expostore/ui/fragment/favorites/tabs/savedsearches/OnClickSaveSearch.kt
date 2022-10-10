package com.expostore.ui.fragment.favorites.tabs.savedsearches

import com.expostore.model.SaveSearchModel
import com.expostore.ui.fragment.search.filter.models.FilterModel

interface OnClickSaveSearch {
    fun onClickSaveSearch(model:SaveSearchModel)
    fun onClickLike(id:String)
}