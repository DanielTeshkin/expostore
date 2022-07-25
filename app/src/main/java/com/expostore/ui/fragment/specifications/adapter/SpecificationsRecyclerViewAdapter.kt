package com.expostore.ui.fragment.specifications.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.adapter.holder.MultiSpecificationHolder
import com.expostore.ui.fragment.specifications.adapter.holder.SingleSpecificationHolder
import com.expostore.ui.fragment.specifications.adapter.holder.SpecificationViewHolder
import com.expostore.ui.fragment.specifications.adapter.holder.TypeHolder
import com.google.android.material.bottomsheet.BottomSheetDialog

class SpecificationsRecyclerViewAdapter(
    private var specifications: List<ProductCategoryModel>,
    private val context: Context,
    private val onClickSelectionCategory: CategoryChose,
   private val bottomSheetDialog: BottomSheetDialog
) : RecyclerView.Adapter<SpecificationViewHolder>() {

    private val typeHolder=TypeHolder(context,onClickSelectionCategory ,bottomSheetDialog)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        return typeHolder.createHolder(viewType,parent)
    }



    override fun getItemViewType(position: Int): Int = typeHolder.getType(specifications[position].have_child)

    override fun getItemCount(): Int = specifications.size



    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        when(holder){
            is SingleSpecificationHolder->{

                holder.bind(specifications[position])}
            is MultiSpecificationHolder-> holder.bind(specifications[position])
        }


    }



}