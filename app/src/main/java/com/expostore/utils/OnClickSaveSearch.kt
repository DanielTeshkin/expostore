package com.expostore.utils

import com.expostore.model.SaveSearchModel

interface OnClickSaveSearch {
    fun onClickSaveSearch(saveSearchModel: SaveSearchModel)
    fun onClickLike(id:String)
}