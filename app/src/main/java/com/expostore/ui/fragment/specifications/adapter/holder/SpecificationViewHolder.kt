package com.expostore.ui.fragment.specifications.adapter.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.adapter.utils.OnClickSelectionCategory

abstract class SpecificationViewHolder(
    view:View,
    context:Context,
    onClickSelectionCategory: CategoryChose
):RecyclerView.ViewHolder(view) {

   abstract  fun bind(item:ProductCategoryModel)
}