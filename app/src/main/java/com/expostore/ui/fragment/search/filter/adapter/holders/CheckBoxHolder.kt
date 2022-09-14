package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import com.expostore.databinding.CheckBoxItemBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.general.CharacteristicState
import com.expostore.ui.general.CharacteristicsStateModel

class CheckBoxHolder(
    private val binding: CheckBoxItemBinding,
    context: Context,
    private val state: CharacteristicState?,
    private val characteristicsStateModel: CharacteristicsStateModel?
):BaseFilterHolder(binding.root,context,state) {
    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
     binding.checkbox.text=categoryCharacteristicModel.name
        if (characteristicsStateModel!=null) binding.checkbox.isChecked= characteristicsStateModel.checkBoxStateModel.state.get(categoryCharacteristicModel.id)
            ?:false
        binding.checkbox.setOnCheckedChangeListener { _, b ->
            state?.checkBoxListener(categoryCharacteristicModel.id,b)
        }
    }
}