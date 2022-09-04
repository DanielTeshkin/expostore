package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.expostore.databinding.SelectFilterItemBinding
import com.expostore.databinding.SelectedItemsBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ValueModel
import com.expostore.ui.general.CharacteristicState
import com.expostore.ui.general.CharacteristicsStateModel

class SelectFilterHolder(
    private val binding: SelectFilterItemBinding,
    private val contextOur: Context,
    val state: CharacteristicState?,
   private val characteristicsStateModel: CharacteristicsStateModel?

):BaseFilterHolder(binding.root,contextOur,state) {
         private lateinit var checkAdapter: CheckBoxAdapter
    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
        val name=categoryCharacteristicModel.name
        binding.title.text=name
        checkAdapter=CheckBoxAdapter(categoryCharacteristicModel.listValue,categoryCharacteristicModel.id,state,characteristicsStateModel)
        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(contextOur,LinearLayoutManager.HORIZONTAL,false)
            adapter= checkAdapter
        }
        }

}




class CheckBoxAdapter(
    val list: List<ValueModel>,
    val name: String,
    val state: CharacteristicState?,
    val characteristicsStateModel: CharacteristicsStateModel?

    ):RecyclerView.Adapter<CheckBoxAdapter.CheckBoxViewHolder>(){
    private val listStates= mutableListOf<String>()
   inner class CheckBoxViewHolder( val binding: SelectedItemsBinding):RecyclerView.ViewHolder(binding.root) {
          fun bind(valueModel: ValueModel){
              binding.selected.text=valueModel.value
             val list= characteristicsStateModel?.selectStateModel?.state?.get(name)
             binding.selected.isChecked=list?.contains(valueModel.id) == true
              binding.selected.setOnCheckedChangeListener { _, checked ->
                  if (checked)binding.selected.setTextColor(Color.WHITE)
                   else binding.selected.setTextColor(Color.BLACK)
                 state?.selectListener(valueModel.id?:"",name,checked)
              }
          }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckBoxViewHolder {
        return CheckBoxViewHolder(SelectedItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CheckBoxViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int =list.size


}