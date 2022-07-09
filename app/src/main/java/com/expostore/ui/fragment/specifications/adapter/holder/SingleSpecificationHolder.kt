package com.expostore.ui.fragment.specifications.adapter.holder

import android.content.Context
import com.expostore.databinding.CategorySingleItemBinding
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.specifications.adapter.utils.OnClickSelectionCategory


class SingleSpecificationHolder(val binding:CategorySingleItemBinding,
                                val context: Context,
                                val onClickSelectionCategory: OnClickSelectionCategory) :SpecificationViewHolder(binding.root,context,onClickSelectionCategory)  {

    override fun bind(item: ProductCategoryModel) {
       binding.category.text=item.name
       binding.category.click { onClickSelectionCategory.onClickSingleSelection(item) }
    }
}