package com.expostore.ui.base.vms

import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.interactors.BaseItemsInteractor
import com.expostore.ui.base.interactors.BaseTenderInteractor

abstract class BaseTenderViewModel: BaseItemViewModel<TenderModel, SelectFavoriteTenderResponseData, FavoriteTender>()
{
    abstract override val interactor: BaseTenderInteractor
    override fun onStart() {

    }

    override fun navigateToItem(model: TenderModel) {

    }
}