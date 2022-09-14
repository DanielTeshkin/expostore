package com.expostore.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.category.OnClickListener
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet

abstract class BaseProductListFragment <Binding : ViewBinding>(private val inflate: Inflate<Binding>) :
    BaseFragment<Binding>(inflate), OnClickBottomSheetFragment {
        abstract val viewModel:BaseProductViewModel
        open val intoPersonalSelection:Boolean=false



    override fun onStart() {
        super.onStart()
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(selections) { state ->
                handleState(state) { handleSelections(it) }
            }
        }
    }
    private fun handleSelections(list: List<SelectionModel>){
        loadSelections(list)
    }
    abstract fun loadSelections(list: List<SelectionModel>)
    override fun createSelection(product: String) = viewModel.navigateToCreateSelection(product)
    override fun addToSelection(id: String, product: String)= viewModel.addToSelection(id,product)
    override fun call(username: String) =navigateToCall(username)
    override fun createNote(product: ProductModel)=viewModel.navigateToNote(product)
    override fun chatCreate(id: String) =viewModel.createChat(id)
    override fun share(id: String){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, id)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        ContextCompat.startActivity(requireContext(), shareIntent, null)
    }
    override fun block() =viewModel.navigateToBlock()
    override fun addToComparison(id: String) =viewModel.comparison(id)
    protected fun getClickListener(list:List<SelectionModel>) =
        object : OnClickListener {
            override fun onClickLike(id: String) = viewModel.updateSelected(id)
            override fun onClickProduct(model: ProductModel) = viewModel.navigateToProduct(model)
            override fun onClickMessage(model: ProductModel) = viewModel.createChat(model.id)
            override fun onClickCall(phone: String) = navigateToCall(phone)
            override fun onClickAnother(model: ProductModel) =
                showBottomSheet(
                   requireContext(),
                    model,
                    list = list,
                    onClickBottomFragment = this@BaseProductListFragment,
                    intoPersonalSelection
                )
        }
    }