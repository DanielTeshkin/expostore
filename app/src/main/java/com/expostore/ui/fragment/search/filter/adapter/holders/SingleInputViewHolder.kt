package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.expostore.databinding.SingleInptutItemBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.general.CharacteristicState

import com.expostore.ui.general.CharacteristicsStateModel

class SingleInputViewHolder(
    val binding: SingleInptutItemBinding,
    private val myContext: Context,
    private val state: CharacteristicState?,
    private val characteristicsStateModel: CharacteristicsStateModel?
):BaseFilterHolder(
    binding.root,myContext,state
                            ) {
    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
        binding.title.text=categoryCharacteristicModel.name
        if (characteristicsStateModel!=null) binding.start.setText(characteristicsStateModel.inputStateModel?.state
            ?.get(categoryCharacteristicModel.name)?.first)
        binding.start.addTextChangedListener {
            state?.inputListener(left = it.toString(), name = categoryCharacteristicModel.id)
        }
    }
}