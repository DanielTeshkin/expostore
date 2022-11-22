package com.expostore.ui.fragment.favorites.tabs.personal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.PersonalProductsFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.general.other.PersonalProductListener
import com.expostore.ui.general.other.showPersonalProductBottomSheet


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabPersonalProductFragment() :
    BaseFragment<PersonalProductsFragmentBinding>(PersonalProductsFragmentBinding::inflate),PersonalProductListener {
    private val viewModel: TabPersonalProductViewModel by viewModels()
    val personal= mutableListOf<ProductModel>()
    private val myAdapter :TabPersonalProductRecyclerViewAdapter by lazy { TabPersonalProductRecyclerViewAdapter(personal) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val init:Show<List<ProductModel>> ={init(it)}
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(comparison){snackbarOpen()}
            subscribe(ui){handleState(it,init)}
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
        binding.createPersonal.click { viewModel.navigateToAddPersonalProduct() }
    }

    fun init(products:List<ProductModel>){
        if (personal.size<0) personal.addAll(products)
        myAdapter.onItemClick ={viewModel.navigate(it)}
        myAdapter.onAnotherClick={ showPersonalProductBottomSheet(requireContext(),it,this)}
        binding.rvUsers.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=myAdapter
        }
        binding.progressBar13.visibility=View.GONE
    }

    override fun deletePersonalProduct(model: ProductModel){
         personal.remove(model)
        myAdapter.notifyDataSetChanged()
        viewModel.deletePersonal(model.id)
    }

    override fun navigateToComparison() =viewModel.navigateToComparison()
    override fun addToComparison(id: String)=viewModel.addToComparison(id)

    override fun createOrUpdateNote(model: ProductModel) = viewModel.navigateToNote(model)

}