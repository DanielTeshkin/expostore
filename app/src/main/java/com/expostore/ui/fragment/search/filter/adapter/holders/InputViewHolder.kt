package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.expostore.databinding.DoubleInptutItemBinding
import com.expostore.databinding.SingleInptutItemBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.fragment.search.filter.adapter.utils.FilterState

class InputViewHolder(val binding:DoubleInptutItemBinding, private val myContext:Context,private  val state: FilterState?):BaseFilterHolder(binding.root,myContext,state) {

    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
        binding.apply {
            title.text = categoryCharacteristicModel.name
            start.hint="От"
            end.hint="До"
            start.addTextChangedListener {start->
                  state?.inputListener(start.toString(),end.text.toString(),categoryCharacteristicModel.name)

            }
            end.addTextChangedListener { end->
                Log.i("not",start.toString())
                state?.inputListener(start.text.toString(),end.toString(),categoryCharacteristicModel.name)
            }

        }

    }
}