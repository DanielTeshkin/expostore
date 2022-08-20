package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.general.FilterState

abstract class BaseFilterHolder(val view:View, val context:Context,private  val filterState: FilterState?):RecyclerView.ViewHolder(view) {

    abstract fun bind(categoryCharacteristicModel: CategoryCharacteristicModel)
}