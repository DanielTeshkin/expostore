package com.expostore.ui.fragment.favorites.tabs.personal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.PersonalProductsFragmentBinding
import com.expostore.model.product.ProductModel


import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabPersonalProductFragment() :
    BaseFragment<PersonalProductsFragmentBinding>(PersonalProductsFragmentBinding::inflate) {
    private val viewModel: TabPersonalProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

                val init:Show<List<ProductModel>> ={init(it)}
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(ui){handleState(it,init)}
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
        binding.btnAddAdvertisement.click {
            viewModel.navigateToAddPersonalProduct()
        }
    }

    fun init(products:List<ProductModel>){
        val personal= mutableListOf<ProductModel>()
        personal.addAll(products)
        val myAdapter=TabPersonalProductRecyclerViewAdapter(personal)
        myAdapter.onItemClick ={viewModel.navigate(it)}
        myAdapter.onDeleteClick={
            viewModel.deletePersonal(it)

        }
        binding.rvUsers.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=myAdapter
        }
    }

}