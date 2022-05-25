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
import com.expostore.databinding.ArchiveFragmentBinding
import com.expostore.databinding.PublicProductFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchiveFragment : BaseFragment<ArchiveFragmentBinding>(ArchiveFragmentBinding::inflate){

    private val viewModel:ArchiveViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }

    fun subscribeViewModel(){
        viewModel.load()
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
        val onClickMyProduct=object :OnClickMyProduct{
            override fun click() {
                viewModel.navigate()
            }

        }
        binding.archiveRv.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter=MyProductAdapter(list,onClickMyProduct)

        }
    }


}