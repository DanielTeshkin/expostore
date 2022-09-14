package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.expostore.databinding.DoubleInptutItemBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.general.CharacteristicState
import com.expostore.ui.general.CharacteristicsStateModel

class InputViewHolder(
    val binding: DoubleInptutItemBinding,
    private val myContext: Context,
    private val state: CharacteristicState?,
    private val characteristicsStateModel: CharacteristicsStateModel?
):BaseFilterHolder(binding.root,myContext,state) {

    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
        binding.apply {
            title.text = categoryCharacteristicModel.name
            start.hint="От"
            end.hint="До"
            if (characteristicsStateModel!=null) {
                start.setText(
                    characteristicsStateModel.inputStateModel.state
                        ?.get(categoryCharacteristicModel.name)?.first
                )
                end.setText(
                    characteristicsStateModel.inputStateModel.state
                        .get(categoryCharacteristicModel.name)?.second
                )

            }
            start.addTextChangedListener {start->
                  state?.inputListener(start.toString(),end.text.toString(),categoryCharacteristicModel.id)

            }
            end.addTextChangedListener { end->
                Log.i("not",start.toString())
                state?.inputListener(start.text.toString(),end.toString(),categoryCharacteristicModel.id)
            }

        }

    }
}