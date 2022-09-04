package com.expostore.ui.fragment.comparison

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.expostore.R
import com.expostore.databinding.ComparisonFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComparisonFragment : BaseFragment<ComparisonFragmentBinding>(ComparisonFragmentBinding::inflate) {
    private val viewModel :ComparisonViewModel by viewModels()
    private val comparisonAdapter:ComparisonAdapter by lazy {ComparisonAdapter()}



    override fun onStart() {
        super.onStart()
        viewModel.apply {
            subscribe(products){initAdapter(it)}
            subscribe(currentPositionFirst) { binding.current.text = "$it из ${products.value.size}" }
            subscribe(currentPositionSecond){binding.currentN.text="$it из ${products.value.size}"}
            subscribe(comparisons){
                comparisonAdapter.addModels(it) }
        }
        initComparisonAdapter()
    }

    private fun initAdapter(list: List<ProductModel>){
        binding.apply {
            val myAdapter=PagerComparisonAdapter()
            myAdapter.items=list
            myAdapter.onDeleteClickListener={viewModel.deleteFromComparison(it)}
            myAdapter.onGoClickListener ={ viewModel.navigateToProduct(it)}
            pager1.adapter=PagerComparisonAdapter()
            pager2.adapter=PagerComparisonAdapter()
            pager1.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel
                        .comparison(position,pager2.currentItem)
                }
            })
            pager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel
                        .comparison(pager1.currentItem,position)
                }
            })
            next.click {
                if(pager1.currentItem!=list.size) {
                    pager1.currentItem = +1
                    viewModel.changePosition1(pager1.currentItem)
                }
            }
            previous.click {
                if(pager1.currentItem!=0) {
                    pager1.currentItem = -1
                    viewModel.changePosition1(pager1.currentItem)
                }
            }
            nexct.click {
                if(pager2.currentItem!=list.size) {
                    pager2.currentItem = pager2.currentItem + 1
                    viewModel.changePosition2(pager2.currentItem)
                }
            }
            previousNext.click {
                if( pager2.currentItem!=0) {
                    pager2.currentItem = -1
                    viewModel.changePosition2(pager2.currentItem)
                }
            }

        }
    }

    private fun initComparisonAdapter(){
        binding.rvComparison.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=comparisonAdapter
        }
    }



}