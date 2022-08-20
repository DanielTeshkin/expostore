package com.expostore.ui.fragment.product.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.CharacteristicBottomSheetBinding
import com.expostore.databinding.CharacteristicItemBinding
import com.expostore.databinding.FilterBottomSheetBinding
import com.expostore.model.product.Character
import com.expostore.utils.CategoryRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

fun openBottomSheet(list:List<Character>,context:Context){
    val binding= CharacteristicBottomSheetBinding.inflate(LayoutInflater.from(context))
    val bottomSheetDialog= BottomSheetDialog(context)
    bottomSheetDialog.setContentView(binding.root)
    binding.rvCharcteristics.apply {
        layoutManager=LinearLayoutManager(context)
        adapter=CharacteristicsAdapter(list)
    }
    bottomSheetDialog.show()
}
class CharacteristicsAdapter(private val list: List<Character>):
    RecyclerView.Adapter<CharacteristicsAdapter.CharacterViewHolder>(){
        inner class CharacterViewHolder(val binding: CharacteristicItemBinding):RecyclerView.ViewHolder(binding.root){
                     fun bind(model:Character){
                         binding.nameCharacter.text=model.characteristic?.name
                         when(model.characteristic?.type){
                           "input" -> binding.mean.text=model.char_value
                             "radio" -> binding.mean.text=model.selected_item?.value
                             "select" -> binding.mean.text=model.selected_item?.value
                             "checkbox" ->binding.mean.text=model.bool_value.toString()
                         }
                     }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CharacteristicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int =list.size
}
