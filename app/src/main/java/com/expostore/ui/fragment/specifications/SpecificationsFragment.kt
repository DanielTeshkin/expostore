package com.expostore.ui.fragment.specifications

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.SpecificationsFragmentBinding
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.specifications.adapter.SpecificationsRecyclerViewAdapter
import com.expostore.ui.fragment.specifications.adapter.utils.OnClickSelectionCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecificationsFragment :
    BaseFragment<SpecificationsFragmentBinding>(SpecificationsFragmentBinding::inflate) {

    private  val specificationsViewModel: SpecificationsViewModel by viewModels()
    private lateinit var onClickSelectionCategory: OnClickSelectionCategory
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        installSetFragmentListeners()
        val show:Show<List<ProductCategoryModel>>  = { showCategories(it) }
        specificationsViewModel.apply {
            subscribe(categories){handleState(it,show)}
            subscribe(navigation){navigateSafety(it)}
            getCategories()
        }
    }

   private fun showCategories(list: List<ProductCategoryModel>){
              binding.rvSpecifications.apply {

                //  val myAdapter=SpecificationsRecyclerViewAdapter(list,requireContext(),onClickSelectionCategory)
                //  layoutManager=LinearLayoutManager(requireContext())
               //   adapter=myAdapter
              } }

    fun installSetFragmentListeners(){
        setFragmentResultListener("requestKey"){_,bundle->
            val result=bundle.getString("flag")
            if(result!=null) specificationsViewModel.saveFlag(result)
        }
        setFragmentResultListener("key"){_,bundle ->
            val result=bundle.getParcelable<DataModel>("data")
            onClickSelectionCategory=object :OnClickSelectionCategory{
                override fun onClickSingleSelection(item: ProductCategoryModel) {
                    setFragmentResult("category", bundleOf("intent" to item))
                    setFragmentResult("new_key", bundleOf("info" to result))
                    specificationsViewModel.navigate()
                } }

        }
    }

}