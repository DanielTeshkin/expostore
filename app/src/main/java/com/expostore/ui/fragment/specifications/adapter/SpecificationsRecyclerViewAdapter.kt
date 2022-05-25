package com.expostore.ui.fragment.specifications.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.specifications.adapter.holder.MultiSpecificationHolder
import com.expostore.ui.fragment.specifications.adapter.holder.SingleSpecificationHolder
import com.expostore.ui.fragment.specifications.adapter.holder.SpecificationViewHolder
import com.expostore.ui.fragment.specifications.adapter.holder.TypeHolder
import com.expostore.ui.fragment.specifications.adapter.utils.OnClickSelectionCategory

import kotlinx.android.synthetic.main.specification_item.view.*

class SpecificationsRecyclerViewAdapter(private var specifications: List<ProductCategoryModel>,
                                        private val context:Context, private val onClickSelectionCategory: OnClickSelectionCategory) : RecyclerView.Adapter<SpecificationViewHolder>() {

    private val typeHolder=TypeHolder(context,onClickSelectionCategory)


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