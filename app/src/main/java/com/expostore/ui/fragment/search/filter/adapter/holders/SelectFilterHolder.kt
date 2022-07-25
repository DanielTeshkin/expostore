package com.expostore.ui.fragment.search.filter.adapter.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.CheckBoxItemBinding

import com.expostore.databinding.SelectFilterItemBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ValueModel
import com.expostore.ui.fragment.search.filter.adapter.utils.FilterState

class SelectFilterHolder(
    private val binding: SelectFilterItemBinding,
    private val contextOur: Context,
    val state: FilterState?

):BaseFilterHolder(binding.root,contextOur,state) {
         private lateinit var checkAdapter: CheckBoxAdapter
    override fun bind(categoryCharacteristicModel: CategoryCharacteristicModel) {
        val name=categoryCharacteristicModel.name
        binding.title.text=name
        checkAdapter=CheckBoxAdapter(categoryCharacteristicModel.listValue,categoryCharacteristicModel.id,state)
        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(contextOur)
            adapter= checkAdapter
        }
        }

}




class CheckBoxAdapter(
    val list: List<ValueModel>,
    val name: String,
    val state: FilterState?,

):RecyclerView.Adapter<CheckBoxAdapter.CheckBoxViewHolder>(){
    private val listStates= mutableListOf<String>()
   inner class CheckBoxViewHolder( val binding: CheckBoxItemBinding):RecyclerView.ViewHolder(binding.root) {
          fun bind(valueModel: ValueModel){
              binding.checkbox.text=valueModel.value
              binding.checkbox.setOnCheckedChangeListener { _, checked ->
                 state?.selectListener(valueModel.id?:"",name,checked)
              }
          }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckBoxViewHolder {
        return CheckBoxViewHolder(CheckBoxItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CheckBoxViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int =list.size


}