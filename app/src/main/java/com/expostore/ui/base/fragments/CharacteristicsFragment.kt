package com.expostore.ui.base.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.vms.CharacteristicViewModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicState

abstract class CharacteristicsFragment<Binding : ViewBinding>(private val inflate: Inflate<Binding>) :
    BaseFragment<Binding>(inflate), CharacteristicState {
      abstract val viewModel:CharacteristicViewModel
      abstract val filter:String
      private val characteristicAdapter: CharacteristicInputRecyclerAdapter by lazy {
        CharacteristicInputRecyclerAdapter(requireContext(),
            this,filter,viewModel.characteristicState.value)
    }
    abstract val categoriesLayout: LinearLayout
    abstract val characteristicsLayout: LinearLayout?
    abstract val rvCharacteristics:RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            start(findNavController())
            subscribe(navigation) { navigateSafety(it) }
            subscribe(categories){ state -> handleState(state) { handleCategories(it) } }
            subscribe(characteristics){ state -> handleState(state) { handleCharacteristics(it) } }
        }
    }

     private fun handleCategories(items:List<ProductCategoryModel>){
         categoriesLayout.click {
             val categoryChose: CategoryChose = { viewModel.saveCategory(it.id) }
             showBottomSpecifications(items, requireContext(), categoryChose)
         }
     }
     private fun handleCharacteristics(items:List<CategoryCharacteristicModel>){
         characteristicsLayout?.apply {
             visibility = View.VISIBLE
             selectListener {
                 rvCharacteristics.isVisible=it
             }
         }
         characteristicAdapter.removeAll()
         characteristicAdapter.addElement(items)
         rvCharacteristics.apply {
             isNestedScrollingEnabled = false
             layoutManager=LinearLayoutManager(requireContext())
             adapter=characteristicAdapter
         }
     }
     override fun inputListener(left: String, right: String?, name: String) =viewModel.inputListener(left, right, name)
     override fun radioListener(id: String, name: String)=viewModel.radioListener(id, name)
     override fun selectListener(id: String, name: String, checked: Boolean) = viewModel.selectListener(id, name, checked)
     override fun checkBoxListener(name: String, checked: Boolean) = viewModel.checkBoxListener(name, checked)


 }