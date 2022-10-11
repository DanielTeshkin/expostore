package com.expostore.ui.base.fragments

import android.content.Context
import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.vms.BaseProductViewModel
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import com.google.android.material.snackbar.Snackbar


abstract class BaseProductFragment<Binding : ViewBinding>(private val inflate: Inflate<Binding>)
    : BaseItemFragment<Binding,ProductModel, SelectFavoriteResponseData,
    FavoriteProduct>(inflate){
    abstract override val viewModel: BaseProductViewModel
    override fun onStart() {
        super.onStart()
        viewModel.apply {
            subscribe(selections) { state -> handleState(state) { loadSelections(it) } }
            subscribe(comparison){handleState(it){snackbarOpen()} }
        }
    }
    abstract fun loadSelections(list: List<SelectionModel>)
    override fun addToComparison(id: String) =viewModel.addToComparison(id)
    override fun createSelection(product: String) = viewModel.navigateToCreateSelection(product)
    override fun addToSelection(id: String, product: String)= viewModel.addToSelection(id,product)
    override fun block(model: ProductModel) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("service.ibuyer@yandex.ru"))
        startActivity(Intent.createChooser(intent, "Пожаловатьcя на товар ${model.id}"))
    }

    override fun navigateToComparison() { viewModel.navigateToComparison() }
    override fun showBottomScreen(
        context: Context,
        item: ProductModel,
        list: List<SelectionModel>?,
        onClickBottomFragment: OnClickBottomSheetFragment<ProductModel>,
        mean: Boolean
    ) {
        showBottomSheet(context,item,list,onClickBottomFragment,mean)
    }
    }


