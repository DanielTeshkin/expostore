package com.expostore.ui.fragment.specifications.adapter.holder

import android.content.Context
import com.expostore.databinding.CategorySingleItemBinding
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.specifications.CategoryChose
import com.google.android.material.bottomsheet.BottomSheetDialog


class SingleSpecificationHolder(
  private  val binding: CategorySingleItemBinding,
   private val context: Context,
  private  val onClickSelectionCategory: CategoryChose,
   private val bottomSheetDialog: BottomSheetDialog
) :SpecificationViewHolder(binding.root,context,onClickSelectionCategory)  {
    override fun bind(item: ProductCategoryModel) {
       binding.category.text=item.name
       binding.category.click {
           bottomSheetDialog.hide()
           onClickSelectionCategory(item)
       }
    }
}