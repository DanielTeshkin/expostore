package com.expostore.ui.base.fragments

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.vms.BaseItemViewModel
import com.expostore.ui.fragment.category.OnClickListeners
import com.expostore.ui.general.other.OnClickBottomSheetFragment

abstract class BaseItemFragment <Binding : ViewBinding,T : Any,A,E>(private val inflate: Inflate<Binding>) :
    BaseFragment<Binding>(inflate), OnClickBottomSheetFragment<T> {
        abstract val viewModel: BaseItemViewModel<T,A,E>
        open val intoPersonalSelection:Boolean=false
        protected val products= mutableListOf<ProductModel>()
        open var isTenders: Boolean=false


    override fun onResume() {
        super.onResume()
        viewModel.apply { subscribe(navigation){navigateSafety(it)} }
    }
    protected fun handleSelections(list: List<SelectionModel>){
        loadSelections(list)
    }
    open fun loadSelections(list: List<SelectionModel>){}
    override fun createSelection(product: String) = viewModel.navigateToCreateSelection(product)
    override fun addToSelection(id: String, product: String)= viewModel.addToSelection(id,product)
    override fun call(username: String) =navigateToCall(username)
    override fun createNote(item: T)=viewModel.navigateToNote(item)
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
    override fun addToComparison(id: String) =viewModel.addToComparison(id)
    protected fun updateFavorites(id:String) = viewModel.updateSelected(id)
    abstract fun showBottomScreen(context: Context, item:T, list: List<SelectionModel>,
                                 onClickBottomFragment:OnClickBottomSheetFragment<T>, mean:Boolean)


    protected fun getClickListener(list:List<SelectionModel>) =
        object : OnClickListeners<T> {
            override fun onClickLike(id: String) = updateFavorites(id)
            override fun onClickItem(model: T) = viewModel.navigateToItem(model)
            override fun onClickMessage(id: String)=chatCreate(id)
            override fun onClickCall(phone: String) = navigateToCall(phone)
            override fun onClickAnother(model: T) =
                showBottomScreen( requireContext(),
                    model,
                    list = list,
                    onClickBottomFragment = this@BaseItemFragment,
                    intoPersonalSelection
                )
        }

    }