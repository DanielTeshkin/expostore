package com.expostore.ui.fragment.favorites

import com.expostore.model.SaveSearchModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender

interface FavoritesClickListener {
    fun onClickSelection(selectionModel: SelectionModel, flag: String)
    fun onClickSaveSearch(saveSearchModel: SaveSearchModel)
    fun onClickProduct(favoriteProduct: FavoriteProduct)
    fun onClickTender(favoriteProduct: FavoriteTender)
}