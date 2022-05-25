package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import com.expostore.databinding.CheckBoxItemBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.fragment.search.filter.adapter.utils.FilterState

class CheckBoxHolder(private val binding:CheckBoxItemBinding,context: Context,private val state: FilterState?):BaseFilterHolder(binding.root,context,state) {
    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
     binding.checkbox.text=categoryCharacteristicModel.name
        binding.checkbox.setOnCheckedChangeListener { _, b ->
            state?.checkBoxListener(categoryCharacteristicModel.name,b)
        }
    }
}