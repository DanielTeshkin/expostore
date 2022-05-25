package com.expostore.ui.fragment.product.myproducts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.PublicProductFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.product.myproducts.adapter.MyProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublicProductFragment( val onClickMyProduct: OnClickMyProduct) : BaseFragment<PublicProductFragmentBinding>(PublicProductFragmentBinding::inflate){

    private val viewModel:MyProductsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()

    }

    private fun subscribeViewModel(){
        viewModel.loadMyProduct()
        val show:Show<List<ProductModel>> = {showProductList(it)}
        viewModel.apply {
            subscribe(list){handleState(it,show)}
            subscribe(navigation){navigateSafety(it)}
        }
    }

    private fun showProductList(list: List<ProductModel>) {
        binding.apply {

           rvMyProducts.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = MyProductAdapter(list, onClickMyProduct)
           }
            progressBar5.visibility=View.GONE
        }
    }


}