package com.expostore.ui.base.fragments

import android.content.Context
import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.tender.TenderModel
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheetTender

abstract class BaseTenderFragment<Binding : ViewBinding>(private val inflate: Inflate<Binding>)
    : BaseItemFragment<Binding, TenderModel, SelectFavoriteTenderResponseData, FavoriteTender>(inflate) {

    override fun block(model: TenderModel) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("service.ibuyer@yandex.ru"))
        startActivity(Intent.createChooser(intent, "Пожаловатьcя на тендер ${model.id}"))
    }
    override fun showBottomScreen(
        context: Context,
        item: TenderModel,
        list: List<SelectionModel>?,
        onClickBottomFragment: OnClickBottomSheetFragment<TenderModel>,
        mean: Boolean
    ) {
        showBottomSheetTender(context,item,onClickBottomFragment)
    }
}