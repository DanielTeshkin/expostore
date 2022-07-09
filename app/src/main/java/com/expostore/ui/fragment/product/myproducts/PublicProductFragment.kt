package com.expostore.ui.fragment.product.myproducts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.MyProductsFragmentBinding
import com.expostore.databinding.PublicProductFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublicProductFragment : BaseFragment<PublicProductFragmentBinding>(PublicProductFragmentBinding::inflate){

    private val viewModel:MyProductsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }

    private fun subscribeViewModel(){
        viewModel.loadMyProduct()
        viewModel.apply {
            subscribe(list){handleState(it)}
            subscribe(navigation){navigateSafety(it)}
        }
    }

    private fun handleState(state: ResponseState<List<ProductModel>>) {
        when(state){
            is ResponseState.Success->showProductList(state.item)
        }

    }

    private fun showProductList(list: List<ProductModel>) {
        binding.apply {
            val onClickMyProduct = object : OnClickMyProduct {
                override fun click() {
                   viewModel.navigate()
                }
            }
           rvMyProducts.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = MyProductAdapter(list, onClickMyProduct)
           }
            progressBar5.visibility=View.GONE
        }
    }


}