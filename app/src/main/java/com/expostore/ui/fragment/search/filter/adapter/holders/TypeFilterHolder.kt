package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.expostore.databinding.*
import com.expostore.ui.general.CharacteristicState
import com.expostore.ui.general.CharacteristicsStateModel

class TypeFilterHolder(
    private val context: Context,
    private val characteristicState: CharacteristicState?,
    private val flag: String,
    private val characteristicsStateModel: CharacteristicsStateModel?

) {
    fun getType(characteristicType: String):Int =when(characteristicType){
        "radio" -> 0
        "checkbox"->1
         "input"->2
        "select"->3

        else -> {6}
    }


    fun createHolder( type:Int,parent: ViewGroup): BaseFilterHolder {
      return  when(type){
            0->RadioViewHolder(RadioFilterItemBinding
                .inflate(LayoutInflater.from(parent.context),parent,false),context,characteristicState,characteristicsStateModel)
            3-> SelectFilterHolder(SelectFilterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),context,characteristicState,characteristicsStateModel)
            2->{
              return if(flag=="filter") InputViewHolder(DoubleInptutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),context,characteristicState,
                  characteristicsStateModel)
              else SingleInputViewHolder(SingleInptutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                  context,characteristicState,characteristicsStateModel)
            }
            6->InputViewHolder(
                DoubleInptutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                context,
                characteristicState,
                characteristicsStateModel
            )
          else ->CheckBoxHolder(CheckBoxItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),context,characteristicState,characteristicsStateModel)
        }        }
    }
