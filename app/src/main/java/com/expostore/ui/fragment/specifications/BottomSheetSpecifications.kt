package com.expostore.ui.fragment.specifications

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.SpecificationsFragmentBinding
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.specifications.adapter.SpecificationsRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

typealias CategoryChose =  (ProductCategoryModel)->Unit
fun showBottomSpecifications( list: List<ProductCategoryModel>,context:Context,categoryChose:CategoryChose){
    val binding:SpecificationsFragmentBinding= SpecificationsFragmentBinding.inflate(LayoutInflater.from(context))
    val bottomSheetDialog = BottomSheetDialog(context)
    bottomSheetDialog.setContentView(binding.root)
    binding.rvSpecifications.apply {
        val myAdapter=
            SpecificationsRecyclerViewAdapter(list,context,categoryChose,bottomSheetDialog)
        layoutManager= LinearLayoutManager(context)
        adapter=myAdapter
    }
    bottomSheetDialog.show()

}