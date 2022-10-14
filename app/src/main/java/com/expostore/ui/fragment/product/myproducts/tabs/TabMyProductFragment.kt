package com.expostore.ui.fragment.product.myproducts.tabs

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabMyProductFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.fragment.product.myproducts.OnClickMyProduct
import com.expostore.ui.fragment.product.myproducts.adapter.MyProductAdapter
import com.expostore.ui.fragment.product.myproducts.adapter.MyProductClickRepository
import com.expostore.ui.fragment.product.myproducts.adapter.SharedMyProductsModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TabMyProductFragment() : BaseFragment<TabMyProductFragmentBinding>(TabMyProductFragmentBinding::inflate) {
    private val viewModel : TabMyProductViewModel by viewModels()
    private val list:MutableList<ProductModel> = mutableListOf()
    private val sharedModel :SharedMyProductsModel by lazy { arguments?.getParcelable("shared")!! }
    private val myProductAdapter by lazy { MyProductAdapter(list,sharedModel.status) }


    override fun onStart() {
        super.onStart()
        val show : Show<List<ProductModel>> = { showMyTenders(it)}
        viewModel.apply {
            loadMyProduct(sharedModel.status)
            subscribe(list){handleState(it,show)}
            subscribe(navigation){navigateSafety(it)}
        }
    }


    private fun showMyTenders(products: List<ProductModel>) {
        myProductAdapter.onClickMyProduct = initOnClick()
        binding.progressBar11.visibility=View.GONE
        list.clear()
        binding.rvProduct.apply {
            list.addAll(products)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myProductAdapter
        }
    }

    fun initOnClick() = object :OnClickMyProduct{
        override fun click(model: ProductModel) {
            viewModel.navigate(model)
        }

    }


    }



