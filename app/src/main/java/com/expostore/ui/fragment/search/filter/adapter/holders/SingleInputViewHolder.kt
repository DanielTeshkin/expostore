package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.expostore.databinding.DoubleInptutItemBinding
import com.expostore.databinding.SingleInptutItemBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.fragment.search.filter.adapter.utils.FilterState

class SingleInputViewHolder(val binding: SingleInptutItemBinding,
                            private val myContext: Context, private  val state: FilterState?):BaseFilterHolder(
    binding.root,myContext,state
                            ) {
    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
        binding.title.text=categoryCharacteristicModel.name
        binding.start.addTextChangedListener {
            state?.inputListener(left = it.toString(), name = categoryCharacteristicModel.id)
        }
    }
}