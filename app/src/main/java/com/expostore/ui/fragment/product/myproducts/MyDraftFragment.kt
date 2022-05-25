package com.expostore.ui.fragment.product.myproducts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.MyDraftFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.product.myproducts.adapter.MyProductAdapter
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDraftFragment(val onClickMyProduct: OnClickMyProduct) : BaseFragment<MyDraftFragmentBinding>(MyDraftFragmentBinding::inflate){

    private val viewModel:DraftViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }

   private fun subscribeViewModel(){
        val show:Show<List<ProductModel>> = {showProductList(it)}
        viewModel.apply {
            loadDraftProduct()
            subscribe(list){handleState(it,show)}
            subscribe(navigation){navigateSafety(it)}
        }
    }



    private fun showProductList(list: List<ProductModel>) {

        binding.rvDraft.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter= MyProductAdapter(list,onClickMyProduct)

        }

    }


}