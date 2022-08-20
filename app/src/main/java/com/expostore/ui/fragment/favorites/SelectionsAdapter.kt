package com.expostore.ui.fragment.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.SaveSelectionBinding
import com.expostore.databinding.SelectionItemBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.fragment.favorites.tabs.selections.OnClickCategory
import com.expostore.ui.fragment.profile.profile_edit.click

class SelectionsAdapter(private val list: MutableList<SelectionModel>): RecyclerView.Adapter<SelectionsAdapter.SelectionsHolder>() {
    var onClick:((SelectionModel)->Unit)?=null
    inner class SelectionsHolder(val binding: SelectionItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: SelectionModel){
            binding.apply {
                nameSeelction.text=model.name
                when(val count=model.count.toString()){
                    "1"->countProducts.text= "$count объявление"
                    "2","3","4"->countProducts.text= "$count объявления"
                    else -> countProducts.text= "$count объявлений"
                }
                root.click {
                    onClick?.invoke(model)
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionsHolder {
        return SelectionsHolder(SelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SelectionsHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int=list.size
}